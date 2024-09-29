package com.example.ecommerce.services;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order createOrder(User user, List<OrderItem> items) {
        Order order = new Order();
        order.setUser(user);
        order.setItems(items);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            item.setPrice(product.getPrice());
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            // Update stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserId(user.getId());
    }
}