package com.shop.shoppingmall.domain.repository;

import com.shop.shoppingmall.domain.entity.CartEntity;
import com.shop.shoppingmall.domain.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void addCart(String email, Item item) {
        jdbcTemplate.update("INSERT INTO CART (ID, EMAIL, NAME, PRICE) VALUES (?, ?, ?, ?)", item.getId(), email, item.getName(), item.getPrice());
    }

    public List<CartEntity> viewCart(String email) {
        return jdbcTemplate.query("SELECT * FROM CART WHERE EMAIL = ?", new Object[]{email}, cartEntityRowMapper());
    }

    RowMapper<CartEntity> cartEntityRowMapper() {
        return (rs, rowNum) -> new CartEntity(
                rs.getLong("ID"),
                rs.getString("EMAIL"),
                rs.getString("NAME"),
                rs.getInt("PRICE")
        );
    }
}
