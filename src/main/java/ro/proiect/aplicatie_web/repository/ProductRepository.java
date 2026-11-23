package ro.proiect.aplicatie_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.proiect.aplicatie_web.model.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
}