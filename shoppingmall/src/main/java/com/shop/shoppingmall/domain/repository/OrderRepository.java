package com.shop.shoppingmall.domain.repository;

import com.shop.shoppingmall.controller.dto.orderDto.Delivery;
import com.shop.shoppingmall.domain.entity.CartEntity;
import com.shop.shoppingmall.domain.entity.DeliveryEntity;
import com.shop.shoppingmall.domain.entity.OrderEntity;
import com.shop.shoppingmall.domain.entity.UserEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void order(UserEntity entity, int amount) {
        jdbcTemplate.update("INSERT INTO ITEM_ORDER (USER_NAME, USER_PHONE, AMOUNT) VALUES (?, ?, ?)", entity.getEmail(), entity.getPhone(), amount);
    }

    public OrderEntity findLastOrder() {
        return jdbcTemplate.queryForObject("SELECT * FROM ITEM_ORDER WHERE ID = LAST_INSERT_ID()", orderEntityRowMapper());
    }

    public void addOrderItem(Long id, CartEntity e) {
        jdbcTemplate.update("INSERT INTO ORDERED_ITEM (ORDER_NUM, ITEM_ID, PRICE, COUNT, AMOUNT) VALUES (?, ?, ?, ?, ?)", id, e.getItemId(), e.getPrice(), e.getCount(), (e.getPrice() * e.getCount()));
    }

    public void addDelivery(DeliveryEntity del) {
        jdbcTemplate.update("INSERT INTO DELIVERY (ORDER_NO, ORDER_ID, ORDER_PHONE, DEL_NAME, DEL_PHONE, DEL_ADDRESS, DEL_MEMO, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", del.getOrderNo(), del.getOrderId(), del.getOrderPhone(), del.getDelName(), del.getDelPhone(), del.getDelAddress(), del.getMemo(), del.getStatus());
    }

    public List<DeliveryEntity> findByEmail(String userId) {
        try {
            return jdbcTemplate.query("SELECT * FROM DELIVERY WHERE ORDER_ID = ?", new Object[]{userId}, deliveryEntityRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    RowMapper<OrderEntity> orderEntityRowMapper() {
        return (rs, rowNum) -> new OrderEntity(rs.getLong("ID"), rs.getString("USER_NAME"), rs.getString("USER_PHONE"), rs.getInt("AMOUNT"));
    }

    RowMapper<DeliveryEntity> deliveryEntityRowMapper() {
        return (rs, rowNum) -> new DeliveryEntity(
                rs.getLong("ID"),
                rs.getLong("ORDER_NO"),
                rs.getString("ORDER_ID"),
                rs.getString("ORDER_PHONE"),
                rs.getString("DEL_NAME"),
                rs.getString("DEL_PHONE"),
                rs.getString("DEL_ADDRESS"),
                rs.getString("DEL_MEMO"),
                rs.getString("STATUS")
        );
    }

}
