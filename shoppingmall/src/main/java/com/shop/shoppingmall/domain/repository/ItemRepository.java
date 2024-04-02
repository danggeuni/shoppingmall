package com.shop.shoppingmall.domain.repository;

import com.shop.shoppingmall.controller.dto.ItemEditDto;
import com.shop.shoppingmall.domain.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addItem(Item item) {
        jdbcTemplate.update("INSERT INTO ITEM (CODE, NAME, PRICE, STOCK, CREATE_AT, UPDATE_AT, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?)",
                item.getCode(), item.getName(), item.getPrice(), item.getStock(), item.getCreateAt(), item.getUpdateAt(), item.getStatus());
    }

    public List<Item> showItems() {
        return jdbcTemplate.query("SELECT * FROM ITEM WHERE STATUS = 'on'", itemRowMapper());
    }

    public List<Item> manageItems() {
        return jdbcTemplate.query("SELECT * FROM ITEM", itemRowMapper());
    }

    public Item findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM ITEM WHERE ID = ?", new Object[]{id}, itemRowMapper());
    }
    public void editItem(Long id, ItemEditDto dto) {
        jdbcTemplate.update("UPDATE ITEM SET CODE = ?, NAME = ?, PRICE = ?, STOCK = ?, STATUS = ? WHERE ID = ?", dto.getCode(), dto.getName(), dto.getPrice(), dto.getStock(), dto.getStatus(), id);
    }
    public void deleteItem(Long id) {
        jdbcTemplate.update("DELETE FROM ITEM WHERE ID = ?", id);
    }

    RowMapper<Item> itemRowMapper () {
        return (rs, rowNum) -> new Item(
                rs.getLong("ID"),
                rs.getInt("CODE"),
                rs.getString("NAME"),
                rs.getInt("PRICE"),
                rs.getInt("STOCK"),
                rs.getTimestamp("CREATE_AT").toLocalDateTime(),
                rs.getTimestamp("UPDATE_AT").toLocalDateTime(),
                rs.getString("STATUS"));
    }
}
