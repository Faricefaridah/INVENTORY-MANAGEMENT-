package com.ims.app.Config;//package com.ims.app.Config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.micrometer.common.lang.NonNull;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//  private JwtService jwtService;
// private UserDetailsService userDetailsService;
// private TokenRepository tokenRepository;
//
//  @Override
//  protected void doFilterInternal(
//      @NonNull HttpServletRequest request,
//      @NonNull HttpServletResponse response,
//      @NonNull FilterChain filterChain) throws ServletException, IOException {
//    if (request.getServletPath().contains("/api/v1/auth")) {
//      filterChain.doFilter(request, response);
//      return;
//    }
//    final String authHeader = request.getHeader("Authorization");
//    final String jwt;
////    final String userEmail;
//    final String userNationalId;
//
//    System.out.println("Auth Header:-->" + authHeader);
//
//    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//      System.out.println("Not Token....>>>");
//      filterChain.doFilter(request, response);
//      return;
//    }
//
//
//    jwt = authHeader.substring(7);
//    try {
//      userNationalId = jwtService.extractUsername(jwt);
//    } catch (Exception e) {
//      // Invalid access token
//      ApiResponse<?> apiResponse = new ApiResponse<>("Invalid accessToken", null, HttpStatus.UNAUTHORIZED.value());
//      String json = new ObjectMapper().writeValueAsString(apiResponse);
//      response.setContentType("application/json");
//      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//      response.getWriter().write(json);
//      return;
//    }
//
//    System.out.println("The Authenticated User: " + userNationalId);
//    if (userNationalId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userNationalId);
//
//      var isTokenValid = tokenRepository.findByToken(jwt)
//          .map(t -> !t.isExpired() && !t.isRevoked())
//          .orElse(false);
//      if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//            userDetails,
//            null,
//            userDetails.getAuthorities());
//        authToken.setDetails(
//            new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//      } else {
//        ApiResponse<?> apiResponse = new ApiResponse<>("Token is expired or revoked", null,
//            HttpStatus.UNAUTHORIZED.value());
//        String json = new ObjectMapper().writeValueAsString(apiResponse);
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getWriter().write(json);
//        return;
//
//      }
//    }
//    filterChain.doFilter(request, response);
//  }
//}
//
//


//}
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.SignatureException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.List;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//  private final JwtTokenUtil jwtTokenUtil;
//  private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//  private final List<String> excludeUrls = List.of("/api/users/create-admin");
//
//  public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
//    this.jwtTokenUtil = jwtTokenUtil;
//  }
//
//  @Override
//  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//          throws ServletException, IOException {
//
//    // Add CORS headers to the response
//    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//    response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-Requested-With");
//    response.setHeader("Access-Control-Allow-Credentials", "true");
//
//    // Handle preflight requests
//    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//      response.setStatus(HttpServletResponse.SC_OK);
//      return;
//    }
//
//    // Check if the request URI is in the list of excluded URLs
//    String requestURI = request.getRequestURI();
//    logger.info("JwtAuthenticationFilter - Request URI: {}", requestURI);
//
//    if (excludeUrls.contains(requestURI)) {
//      logger.info("JwtAuthenticationFilter - Skipping JWT validation for excluded URL: {}", requestURI);
//      chain.doFilter(request, response);
//      return;
//    }
//
//    String header = request.getHeader("Authorization");
//    if (header == null || !header.startsWith("Bearer ")) {
//      logger.warn("JwtAuthenticationFilter - No JWT token found in request headers");
//      chain.doFilter(request, response);
//      return;
//    }
//
//    String token = header.substring(7); // Extract the token
//    try {
//      if (jwtTokenUtil.validateToken(token)) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(jwtTokenUtil.getSecret())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        String username = claims.getSubject();
//        String role = claims.get("role", String.class);
//
//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
//                username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        logger.info("JwtAuthenticationFilter - Authenticated user: {}", username);
//      } else {
//        logger.warn("JwtAuthenticationFilter - JWT token is invalid");
//        SecurityContextHolder.clearContext();
//      }
//    } catch (ExpiredJwtException e) {
//      logger.warn("JwtAuthenticationFilter - JWT token has expired: {}", e.getMessage());
//      SecurityContextHolder.clearContext();
//    } catch (SignatureException e) {
//      logger.error("JwtAuthenticationFilter - JWT signature does not match locally computed signature: {}", e.getMessage());
//      SecurityContextHolder.clearContext();
//    } catch (Exception e) {
//      logger.error("JwtAuthenticationFilter - Error parsing JWT token: {}", e.getMessage());
//      SecurityContextHolder.clearContext();
//    }
//
//    chain.doFilter(request, response);
//  }
//}




import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;
  private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
  private final List<String> excludeUrls = List.of("/api/users/create-admin");

  public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
          throws ServletException, IOException {

    // Add CORS headers to the response
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-Requested-With");
    response.setHeader("Access-Control-Allow-Credentials", "true");

    // Handle preflight requests
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
      return;
    }

    // Check if the request URI is in the list of excluded URLs
    String requestURI = request.getRequestURI();
    logger.info("JwtAuthenticationFilter - Request URI: {}", requestURI);

    if (excludeUrls.contains(requestURI)) {
      logger.info("JwtAuthenticationFilter - Skipping JWT validation for excluded URL: {}", requestURI);
      chain.doFilter(request, response);
      return;
    }

    String header = request.getHeader("Authorization");

    // Log all headers for debugging
    logger.info("Request Headers: ");
    request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
      logger.info("{}: {}", headerName, request.getHeader(headerName));
    });

    if (header == null || !header.startsWith("Bearer ")) {
      logger.warn("JwtAuthenticationFilter - No JWT token found in request headers");
      chain.doFilter(request, response);
      return;
    }

    String token = header.substring(7); // Extract the token
    try {
      if (jwtTokenUtil.validateToken(token)) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtTokenUtil.getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
        SecurityContextHolder.getContext().setAuthentication(auth);
        logger.info("JwtAuthenticationFilter - Authenticated user: {}", username);
      } else {
        logger.warn("JwtAuthenticationFilter - JWT token is invalid");
        SecurityContextHolder.clearContext();
      }
    } catch (ExpiredJwtException e) {
      logger.warn("JwtAuthenticationFilter - JWT token has expired: {}", e.getMessage());
      SecurityContextHolder.clearContext();
    } catch (SignatureException e) {
      logger.error("JwtAuthenticationFilter - JWT signature does not match locally computed signature: {}", e.getMessage());
      SecurityContextHolder.clearContext();
    } catch (Exception e) {
      logger.error("JwtAuthenticationFilter - Error parsing JWT token: {}", e.getMessage());
      SecurityContextHolder.clearContext();
    }

    chain.doFilter(request, response);
  }
}
