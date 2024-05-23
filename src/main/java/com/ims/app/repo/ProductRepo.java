//package com.ims.app.repo;
//
//import com.ims.app.service.ProductDispatch;
//import com.ims.app.service.ProductRequest;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import com.ims.app.model.Product;
//
//
//@Repository
//
//public interface ProductRepo extends JpaRepository <Product, String> {
//    void add(ProductRequest request);
//
//    void add(ProductDispatch dispatch);
//}
//
//

package com.ims.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ims.app.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    // No need to add custom methods for ProductRequest and ProductDispatch here
    // Spring Data JPA provides basic CRUD operations for Product entity
}
