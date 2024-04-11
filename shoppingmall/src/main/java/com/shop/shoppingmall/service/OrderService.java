package com.shop.shoppingmall.service;

import com.shop.shoppingmall.controller.dto.orderDto.Delivery;
import com.shop.shoppingmall.domain.entity.CartEntity;
import com.shop.shoppingmall.domain.entity.DeliveryEntity;
import com.shop.shoppingmall.domain.entity.OrderEntity;
import com.shop.shoppingmall.domain.entity.UserEntity;
import com.shop.shoppingmall.domain.repository.CartRepository;
import com.shop.shoppingmall.domain.repository.ItemRepository;
import com.shop.shoppingmall.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(UserEntity user, int amount) {
        orderRepository.order(user, amount);
    }

    public OrderEntity findLastOrder() {
        return orderRepository.findLastOrder();
    }

    public void addOrderItem(Long id, CartEntity e, int totalPrice) {
        orderRepository.addOrderItem(id, e);
    }

    public void addDelivery(UserEntity user, Long orderNum, Delivery delivery) {
        DeliveryEntity del = new DeliveryEntity(null, orderNum, user.getEmail(), user.getPhone(), delivery.getOrderName(), delivery.getOrderPhone(), delivery.getOrderAddress(), delivery.getOrderRequest(), "준비");
        orderRepository.addDelivery(del);
    }

    public List<DeliveryEntity> findByEmail(String userId) {
        return orderRepository.findByEmail(userId);
    }
}
