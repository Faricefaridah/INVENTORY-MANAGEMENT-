package com.ims.app.Config;//package com.ims.app.Config;
//
//
//import com.ims.app.User.User;
//import com.ims.app.User.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//@Configuration
//@RequiredArgsConstructor
//public class ApplicationConfig {
//  private UserRepository repository;
////  @Bean
////  public UserDetailsService userDetailsService() {
////    return username -> repository.findByEmail(username)
////        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
////  }
//
//  @Bean
//  public UserDetailsService userDetailsService() {
//    return username -> {
//      Optional<User> userByEmail = Optional.ofNullable(repository.findByUsername(username));
//      if (userByEmail.isPresent()) {
//        return userByEmail.get();
//      }
//
//      Optional<User> userByNationalId = Optional.ofNullable(repository.findByEmail(username));
//      return userByNationalId.orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    };
//  }
//
////    @Bean
////  public UserDetailsService userDetailsService() {
////    return username -> repository.findByNationalId(username)
////        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
////  }
//  @Bean
//  public AuthenticationProvider authenticationProvider() {
//    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//    authProvider.setUserDetailsService(userDetailsService());
//    authProvider.setPasswordEncoder(passwordEncoder());
//    return authProvider;
//  }
//
//  @Bean
//  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//    return config.getAuthenticationManager();
//  }
//
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//}

import com.ims.app.User.User;
import com.ims.app.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
  private final UserRepository repository;

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      Optional<User> userByEmail = Optional.ofNullable(repository.findByUsername(username));
      if (userByEmail.isPresent()) {
        return userByEmail.get();
      }

      Optional<User> userByNationalId = Optional.ofNullable(repository.findByEmail(username));
      return userByNationalId.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    };
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder()); // Setting the password encoder
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Using BCryptPasswordEncoder for password hashing
  }
}
