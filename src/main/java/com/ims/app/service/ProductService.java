package com.ims.app.service;

import java.util.List;
import java.util.Optional;
import com.ims.app.model.Product;

public interface ProductService {
    List<Product> getAllProducts();
    void addProduct(Product product);
    Optional<Product> getProductById(String id);
    boolean deleteProductById(String id);
}
