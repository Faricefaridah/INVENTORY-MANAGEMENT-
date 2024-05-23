package com.ims.app.Config;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private boolean expired;

    @Column(nullable = false)
    private boolean revoked;

    public boolean isTokenExpired() {
        return expired;
    }

    public boolean isRevoked() {
        return revoked;
    }

    // Additional fields like user information can be added as necessary
}
