package ro.proiect.aplicatie_web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.proiect.aplicatie_web.exception.InvalidFileException;
import ro.proiect.aplicatie_web.exception.ProductNotFoundException;
import ro.proiect.aplicatie_web.model.Category;
import ro.proiect.aplicatie_web.model.Product;
import ro.proiect.aplicatie_web.repository.CategoryRepository;
import ro.proiect.aplicatie_web.repository.ProductRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private static final String UPLOAD_DIR = "uploads/";
    private static final List<String> ALLOWED_EXTENSIONS = List.of(".jpg", ".jpeg", ".png", ".pdf");

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " was not found."));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void saveProduct(Product product, Long categoryId, MultipartFile file) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductNotFoundException("Selected category does not exist."));
        product.setCategory(category);

        if (file != null && !file.isEmpty()) {
            String savedFileName = processAndSaveFile(file);
            product.setImageUrl("/uploads/" + savedFileName);
        } else if (product.getImageUrl() == null || product.getImageUrl().isBlank()) {
            product.setImageUrl("/images/default-wood.jpg");
        }

        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Cannot delete. Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }

    private String processAndSaveFile(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.contains(".")) {
            throw new InvalidFileException("Invalid file name.");
        }

        String extension = originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new InvalidFileException("File type not allowed. Only .jpg, .jpeg, .png, and .pdf are accepted.");
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String uniqueFileName = UUID.randomUUID() + extension;
            Path targetPath = uploadPath.resolve(uniqueFileName);

            // Re-encoding images strips hidden steganography payloads
            if (extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png")) {
                BufferedImage cleanImage = ImageIO.read(file.getInputStream());
                if (cleanImage == null) {
                    throw new InvalidFileException("Corrupted or invalid image file.");
                }
                String format = extension.equals(".png") ? "png" : "jpg";
                ImageIO.write(cleanImage, format, new File(targetPath.toString()));
            } else {
                Files.copy(file.getInputStream(), targetPath);
            }

            return uniqueFileName;
        } catch (IOException e) {
            throw new InvalidFileException("Error saving uploaded file: " + e.getMessage());
        }
    }
}