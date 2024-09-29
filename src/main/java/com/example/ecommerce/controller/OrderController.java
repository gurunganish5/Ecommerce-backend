package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.security.UserPrincipal;
import com.example.ecommerce.services.OrderService;
import com.example.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.getUserByUsername(userPrincipal.getUsername());
        Order createdOrder = orderService.createOrder(user, order.getItems());
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.getUserByUsername(userPrincipal.getUsername());
        List<Order> orders = orderService.getOrdersByUser(user);
        return ResponseEntity.ok(orders);
    }
}