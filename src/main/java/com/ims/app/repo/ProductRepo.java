package com.ims.app.repo;

import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ims.app.model.Product;


@Repository

public interface ProductRepo extends JpaRepository <Product, String> {
}
