//package com.ims.app.service;
//
//import com.ims.app.repo.ProductRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import com.ims.app.model.Product;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//
//    @Autowired
//    private ProductRepo productRepo;
//
//    @Override
//    public List<Product> getAllProducts() {
//        return productRepo.findAll();
//    }
//
//    @Override
//    public void addProduct(Product product) {
//        productRepo.save(product);
//    }
//
//    @Override
//    public Optional<Product> getProductById(String id) {
//        return productRepo.findById(id);
//    }
//
//    @Override
//    public boolean deleteProductById(String id) {
//        if (productRepo.existsById(id)) {
//            productRepo.deleteById(id);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void addProductRequest(ProductRequest request) {
//        productRepo.add(request);
//
//    }
//
//    @Override
//    public void dispatchProduct(ProductDispatch dispatch) {
//        productRepo.add(dispatch);
//
//    }
//}

package com.ims.app.service;

import com.ims.app.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.ims.app.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public void addProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteProductById(String id) {
        return false;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public boolean deleteProductById(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void addProductRequest(ProductRequest request) {
        // Logic for handling ProductRequest goes here
        // Example: productRequestService.save(request);
    }

    @Override
    public void dispatchProduct(ProductDispatch dispatch) {
        // Logic for handling ProductDispatch goes here
        // Example: productDispatchService.save(dispatch);
    }
}
