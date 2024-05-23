package com.ims.app.controller;

import com.ims.app.service.ProductDispatch;
import com.ims.app.service.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ims.app.model.Product;
import com.ims.app.service.ProductService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/newProduct")
    public ResponseEntity<String> newProduct(@RequestBody Product product) {
        // Creating dynamic product ID
        String productId = "PD" + (1000 + new Random().nextInt(9000));
        product.setId(Long.valueOf(productId));
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully with ID: " + productId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        boolean deleteSuccess = productService.deleteProductById(id);
        if (deleteSuccess) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/request")
    public ResponseEntity<String> createProductRequest(@RequestBody ProductRequest request) {
        // Creating dynamic request ID
        String requestId = "PR" + (1000 + new Random().nextInt(9000));
        request.setRequestId(requestId);
        request.setRequestDate(new Date());
        productService.addProductRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product request created successfully with ID: " + requestId);
    }
    @PostMapping("/dispatch")
    public ResponseEntity<String> dispatchProduct(@RequestBody ProductDispatch dispatch) {
        // Creating dynamic dispatch ID
        String dispatchId = "DP" + (1000 + new Random().nextInt(9000));
        dispatch.setDispatchId(dispatchId);
        dispatch.setDispatchDate(new Date());
        productService.dispatchProduct(dispatch);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product dispatched successfully with ID: " + dispatchId);
    }

}