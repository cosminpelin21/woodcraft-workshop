package ro.proiect.aplicatie_web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.proiect.aplicatie_web.model.Category;
import ro.proiect.aplicatie_web.model.Product;
import ro.proiect.aplicatie_web.model.User;
import ro.proiect.aplicatie_web.repository.CategoryRepository;
import ro.proiect.aplicatie_web.repository.ProductRepository;
import ro.proiect.aplicatie_web.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            Category chairs = new Category();
            chairs.setName("Chairs");
            chairs.setDescription("Handcrafted solid wood dining and lounge chairs.");
            categoryRepository.save(chairs);

            Category tables = new Category();
            tables.setName("Tables");
            tables.setDescription("Durable oak and walnut dining and coffee tables.");
            categoryRepository.save(tables);

            Category stairs = new Category();
            stairs.setName("Stairs");
            stairs.setDescription("Custom indoor wooden staircases and treads.");
            categoryRepository.save(stairs);

            Product p1 = new Product();
            p1.setTitle("Nordic Oak Dining Chair");
            p1.setDescription("Ergonomic solid oak chair with natural oil finish.");
            p1.setPrice(145.00);
            p1.setWoodType("Solid Oak");
            p1.setDimensions("45 x 50 x 85 cm");
            p1.setImageUrl("https://images.unsplash.com/photo-1503602642458-232111445657?w=600");
            p1.setCategory(chairs);
            productRepository.save(p1);

            Product p2 = new Product();
            p2.setTitle("Rustic Walnut Dining Table");
            p2.setDescription("6-seater heavy walnut table with matte varnish.");
            p2.setPrice(680.00);
            p2.setWoodType("American Walnut");
            p2.setDimensions("180 x 90 x 76 cm");
            p2.setImageUrl("https://images.unsplash.com/photo-1577140917170-285929fb55b7?w=600");
            p2.setCategory(tables);
            productRepository.save(p2);

            Product p3 = new Product();
            p3.setTitle("Floating Beech Wood Staircase");
            p3.setDescription("Modern open-riser staircase made from kiln-dried beech.");
            p3.setPrice(2400.00);
            p3.setWoodType("Steamed Beech");
            p3.setDimensions("320 x 95 x 280 cm");
            p3.setImageUrl("https://images.unsplash.com/photo-1513694203232-719a280e022f?w=600");
            p3.setCategory(stairs);
            productRepository.save(p3);
        }

        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setFullName("Workshop Admin");
            admin.setEmail("admin@woodcraft.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);

            User client = new User();
            client.setFullName("John Doe");
            client.setEmail("client@woodcraft.com");
            client.setPassword(passwordEncoder.encode("client123"));
            client.setRole("ROLE_CLIENT");
            userRepository.save(client);
        }
    }
}