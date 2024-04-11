package com.shop.shoppingmall.domain.repository;

import com.shop.shoppingmall.domain.entity.CartEntity;
import com.shop.shoppingmall.domain.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addCart(String email, Item item, int amount) {
        jdbcTemplate.update("INSERT INTO CART (EMAIL, ITEM_ID, COUNT) VALUES (?, ?, ?)", email, item.getId(), amount);
    }

    public void updateCart(CartEntity entity) {
        jdbcTemplate.update("UPDATE CART SET COUNT = ? WHERE EMAIL = ? AND ITEM_ID = ?", entity.getCount(), entity.getEmail(), entity.getItemId());
    }

    public List<CartEntity> viewCart(String email) {
        return jdbcTemplate.query("SELECT * FROM CART LEFT OUTER JOIN ITEM ON CART.ITEM_ID = ITEM.ID WHERE EMAIL = ?", new Object[]{email}, cartEntityRowMapper());
    }

    public CartEntity findCart(String email, Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM CART LEFT OUTER JOIN ITEM ON CART.ITEM_ID = ITEM.ID WHERE EMAIL = ? AND ITEM_ID = ?", new Object[]{email, id}, cartEntityRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM CART WHERE ID = ?", id);
    }

    RowMapper<CartEntity> cartEntityRowMapper() {
        return (rs, rowNum) -> new CartEntity(
                rs.getLong("ID"),
                rs.getString("EMAIL"),
                rs.getLong("ITEM_ID"),
                rs.getString("NAME"),
                rs.getInt("PRICE"),
                rs.getInt("STOCK"),
                rs.getInt("COUNT")
        );
    }
}
