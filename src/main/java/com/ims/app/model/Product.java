package com.ims.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productdetails")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String productCategory;
    @Column
    private String productName;
    @Column
    private Long rating;
    @Column
    private Long quality;
    @Column
    private Long maximumProducts;
    @Column
    private Long minimumProducts;
    @Column
    private String userName;
    @Column
    private String emailAddress;
    @Column
    private Long phoneNumber;

    public void setId(String productId) {
    }

    public Object getId() {
        return id;
    }
}
