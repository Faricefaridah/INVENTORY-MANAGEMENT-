package com.ims.app.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  @Autowired
  private JwtAuthenticationFilter jwtAuthFilter;

  @Autowired
  private UserDetailsService userDetailsService;

  private  AuthenticationProvider authenticationProvider;


  @Value("${application.client.origin}")
  private String clientOrigin;

  @Bean
  CorsConfigurationSource corsConfigurationSource()
  {
  CorsConfiguration config = new CorsConfiguration();
  config.setAllowCredentials(false);
  // For Security Set to only allow request from a static elastic IP
  config.addAllowedOrigin(clientOrigin);
  config.addAllowedOrigin("*");
  config.addAllowedHeader("*");
  config.addAllowedOrigin("http://localhost:4200/*");
  config.addAllowedMethod("OPTIONS");
  config.addAllowedMethod("HEAD");
  config.addAllowedMethod("GET");
  config.addAllowedMethod("PUT");
  config.addAllowedMethod("POST");
  config.addAllowedMethod("DELETE");
  config.addAllowedMethod("PATCH");
  UrlBasedCorsConfigurationSource source = new
  UrlBasedCorsConfigurationSource();
  source.registerCorsConfiguration("/**", config);
  return source;

  }




  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.cors(AbstractHttpConfigurer::disable);
    http.csrf(AbstractHttpConfigurer::disable);
//   http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
        .requestMatchers(
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html")
        .permitAll()
        .anyRequest().permitAll())
        // .authenticated()
        ;

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }

//  @Bean
//  public ModelMapper modelMapper() {
//    return new ModelMapper();
//  }

}
