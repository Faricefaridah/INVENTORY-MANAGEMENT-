package com.ims.app.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ims.app.enums.UserRole;
import com.ims.app.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Setter
@Getter
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    private String forgotPasswordToken;
    private int loginAttempts;
    private boolean blocked;

    @Enumerated
    private UserRole userRole;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    // Getters and Setters


    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return userRole.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public boolean isBlocked() {
        return blocked;
    }


    // Increment and reset login attempts
    public void incrementLoginAttempts() {
        this.loginAttempts++;
    }

    public void resetLoginAttempts() {
        this.loginAttempts = 0;
    }
}
