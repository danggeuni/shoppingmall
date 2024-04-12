package com.shop.shoppingmall.domain.repository;

import com.shop.shoppingmall.domain.entity.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {

    private final SqlSessionTemplate sql;

    public OrderRepository(SqlSessionTemplate sql) {
        this.sql = sql;
    }

    public void order(Map<String, Object> map) {
        sql.insert("ShoppingMall.order", map);
    }

    public OrderEntity findLastOrder() {
        return sql.selectOne("ShoppingMall.findLastOrder");
    }

    public void addOrderItem(Map<String, Object> map) {
        sql.update("ShoppingMall.addOrderItem", map);
    }

    public void addSingleOrderItem(Map<String, Object> map) {
        sql.insert("ShoppingMall.addSingleOrderItem", map);
    }

    public void addDelivery(DeliveryEntity del) {
        sql.update("ShoppingMall.addDelivery", del);
    }

    public List<DeliveryEntity> findByEmail(String userId) {
        return sql.selectList("ShoppingMall.findByEmail", userId);
    }
}
