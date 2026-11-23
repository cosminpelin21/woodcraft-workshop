package ro.proiect.aplicatie_web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.proiect.aplicatie_web.model.Product;
import ro.proiect.aplicatie_web.service.OrderService;
import ro.proiect.aplicatie_web.service.ProductService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }

    @GetMapping("/orders")
    public String myOrdersPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        log.info("User {} viewed their orders.", userDetails.getUsername());
        model.addAttribute("orders", orderService.getOrdersByUser(userDetails.getUsername()));
        return "orders";
    }

    @PostMapping("/orders/create")
    public String placeOrder(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam Long productId,
                             @RequestParam(defaultValue = "1") Integer quantity) {
        orderService.createOrder(userDetails.getUsername(), productId, quantity);
        log.info("User {} placed an order for product ID {}.", userDetails.getUsername(), productId);
        return "redirect:/orders?success";
    }

    // --- ADMIN ROUTES ---

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("newProduct", new Product());
        return "admin";
    }

    @PostMapping("/admin/products/add")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam Long categoryId,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             @AuthenticationPrincipal UserDetails userDetails) {
        productService.saveProduct(product, categoryId, imageFile);
        log.info("Admin {} added new product: {}", userDetails.getUsername(), product.getTitle());
        return "redirect:/admin?added";
    }

    @PostMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetails userDetails) {
        productService.deleteProduct(id);
        log.info("Admin {} deleted product ID: {}", userDetails.getUsername(), id);
        return "redirect:/admin?deleted";
    }
}