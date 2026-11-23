package ro.proiect.aplicatie_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.proiect.aplicatie_web.model.Category;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}