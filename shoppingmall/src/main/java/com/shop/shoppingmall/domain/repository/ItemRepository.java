package com.shop.shoppingmall.domain.repository;

import com.shop.shoppingmall.domain.entity.ImgFile;
import com.shop.shoppingmall.domain.entity.Item;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private final SqlSessionTemplate sql;

    @Autowired
    public ItemRepository(SqlSessionTemplate sql) {
        this.sql = sql;
    }

    public void addItem(Item item) {
        sql.insert("ShoppingMall.addItem", item);
    }

    public List<Item> showItems() {
        return sql.selectList("ShoppingMall.showItems");
    }

    public List<Item> manageItems() {
        return sql.selectList("ShoppingMall.manageItems");
    }

    public Item findById(Long id) {
        return sql.selectOne("ShoppingMall.findItem", id);
    }

    public void editItem(Map<String, Object> param) {
        sql.update("ShoppingMall.editItem", param);
    }

    public void deleteItem(Long id) {
        sql.delete("ShoppingMall.deleteImg", id);
        sql.delete("ShoppingMall.deleteItem", id);
    }

    public Item getLastItem() {
        return sql.selectOne("ShoppingMall.getLastItem");
    }

    public void addImg(ImgFile imgFile) {
        sql.insert("ShoppingMall.addImg", imgFile);
    }
}
