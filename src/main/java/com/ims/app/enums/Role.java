package com.ims.app.enums;

import com.ims.app.User.User;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole name;

    @ManyToMany(mappedBy = "roles") // Corrected mappedBy attribute
    private Set<User> users;

    // Constructors, Getters, and Setters
}
