package com.shop.shoppingmall.service;

import com.shop.shoppingmall.controller.dto.orderDto.Delivery;
import com.shop.shoppingmall.domain.entity.*;
import com.shop.shoppingmall.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(UserEntity user, int amount) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", user.getName());
        map.put("userPhone", user.getPhone());
        map.put("amount", amount);

        orderRepository.order(map);
    }

    public OrderEntity findLastOrder() {
        return orderRepository.findLastOrder();
    }

    public void addOrderItem(Long id, CartEntity e) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNum", id);
        map.put("itemId", e.getItemId());
        map.put("price", e.getPrice());
        map.put("count", e.getCount());
        map.put("amount", e.getCount() * e.getPrice());

        orderRepository.addOrderItem(map);
    }

    public void addSingleOrderItem(Long id, Item item, Integer amount) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNum", id);
        map.put("itemId", item.getId());
        map.put("price", item.getPrice());
        map.put("count", amount);
        map.put("amount", item.getPrice() * amount);

        orderRepository.addSingleOrderItem(map);
    }

    public void addDelivery(UserEntity user, Long orderNum, Delivery delivery) {
        DeliveryEntity del = new DeliveryEntity(null, orderNum, user.getEmail(), user.getPhone(), delivery.getOrderName(), delivery.getOrderPhone(), delivery.getOrderAddress(), delivery.getOrderRequest(), "준비");
        orderRepository.addDelivery(del);
    }

    public List<DeliveryEntity> findByEmail(String userId) {
        return orderRepository.findByEmail(userId);
    }
}
