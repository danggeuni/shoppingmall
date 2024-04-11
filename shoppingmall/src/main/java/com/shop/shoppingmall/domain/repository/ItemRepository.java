package com.shop.shoppingmall.domain.repository;

import com.shop.shoppingmall.controller.dto.adminDto.ItemEditDto;
import com.shop.shoppingmall.domain.entity.ImgFile;
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
        jdbcTemplate.update("INSERT INTO ITEM (ITEM_CODE, NAME, PRICE, STOCK, CREATE_AT, UPDATE_AT, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?)",
                item.getItemCode(), item.getName(), item.getPrice(), item.getStock(), item.getCreateAt(), item.getUpdateAt(), item.getStatus());
    }

    public List<Item> showItems() {
        return jdbcTemplate.query("SELECT * FROM ITEM LEFT OUTER JOIN IMG ON ITEM.ID = IMG.ID WHERE STATUS = 'on'", itemRowMapper());
    }

    public List<Item> manageItems() {
        return jdbcTemplate.query("SELECT * FROM ITEM LEFT OUTER JOIN IMG ON ITEM.ID = IMG.ID", itemRowMapper());
    }

    public Item findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM ITEM LEFT OUTER JOIN IMG ON ITEM.ID = IMG.ID WHERE ITEM.ID = ?", new Object[]{id}, itemRowMapper());
    }

    public void editItem(Long id, ItemEditDto dto) {
        jdbcTemplate.update("UPDATE ITEM SET ITEM_CODE = ?, NAME = ?, PRICE = ?, STOCK = ?, STATUS = ? WHERE ID = ?", dto.getCode(), dto.getName(), dto.getPrice(), dto.getStock(), dto.getStatus(), id);
    }

    public void deleteItem(Long id) {
        jdbcTemplate.update("DELETE FROM IMG WHERE ID = ?", id);
        jdbcTemplate.update("DELETE FROM ITEM WHERE ID = ?", id);
    }

    public Item getLastArticle() {
        return jdbcTemplate.queryForObject("SELECT * FROM ITEM LEFT OUTER JOIN IMG ON ITEM.ID = IMG.ID WHERE ITEM.ID = LAST_INSERT_ID()", itemRowMapper());
    }

    public void addImg(ImgFile file) {
        jdbcTemplate.update("INSERT INTO IMG (ID, IMG_NAME, IMG_PATH) VALUES (?, ?, ?)", file.getId(), file.getImgName(), file.getImgPath());
    }

    RowMapper<Item> itemRowMapper () {
        return (rs, rowNum) -> new Item(
                rs.getLong("ID"),
                rs.getString("ITEM_CODE"),
                rs.getString("NAME"),
                rs.getInt("PRICE"),
                rs.getInt("STOCK"),
                rs.getTimestamp("CREATE_AT").toLocalDateTime(),
                rs.getTimestamp("UPDATE_AT").toLocalDateTime(),
                rs.getString("STATUS"),
                rs.getString("IMG_PATH"));
    }
}
