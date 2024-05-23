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

    @Column
    private Double price; // Adding price field

    @Transient // This annotation specifies that this field is not to be persisted to the database
    private Double totalAmount; // Adding total amount field which is calculated, not persisted

    // Getter and Setter for price
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // Getter and Setter for total amount
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
