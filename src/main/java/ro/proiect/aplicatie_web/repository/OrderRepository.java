package ro.proiect.aplicatie_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.proiect.aplicatie_web.model.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}