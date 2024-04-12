package com.shop.shoppingmall.domain.repository;
import com.shop.shoppingmall.domain.entity.CartEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CartRepository {
    private final SqlSessionTemplate sql;

    @Autowired
    public CartRepository(SqlSessionTemplate sql) {
        this.sql = sql;
    }

    public void addCart(Map<String, Object> map) {
        sql.update("ShoppingMall.addCart", map);
    }

    public void updateCart(CartEntity entity) {
        sql.update("ShoppingMall.updateCart", entity);
    }

    public List<CartEntity> viewCart(String email) {
        return sql.selectList("ShoppingMall.viewCart", email);
    }

    public CartEntity findCart(Map<String, Object> map) {
        return sql.selectOne("ShoppingMall.findCart", map);
    }

    public void deleteById(Long id) {
        sql.delete("ShoppingMall.deleteById", id);
    }
}
