package ro.proiect.aplicatie_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.proiect.aplicatie_web.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}