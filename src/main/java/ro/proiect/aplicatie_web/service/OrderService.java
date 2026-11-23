package ro.proiect.aplicatie_web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.proiect.aplicatie_web.exception.ProductNotFoundException;
import ro.proiect.aplicatie_web.model.Order;
import ro.proiect.aplicatie_web.model.Product;
import ro.proiect.aplicatie_web.model.User;
import ro.proiect.aplicatie_web.repository.OrderRepository;
import ro.proiect.aplicatie_web.repository.ProductRepository;
import ro.proiect.aplicatie_web.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Order createOrder(String userEmail, Long productId, Integer quantity) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setPaymentStatus("PENDING");

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found."));
        return orderRepository.findByUserId(user.getId());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void markOrderAsPaid(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found."));
        order.setPaymentStatus("PAID");
        orderRepository.save(order);
    }
}