package ro.proiect.aplicatie_web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import ro.proiect.aplicatie_web.exception.InvalidFileException;
import ro.proiect.aplicatie_web.exception.ProductNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFound(ProductNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler({InvalidFileException.class, MaxUploadSizeExceededException.class})
    public String handleFileError(Exception ex, Model model) {
        model.addAttribute("errorMessage", "File upload error: " + ex.getMessage());
        return "error";
    }
}