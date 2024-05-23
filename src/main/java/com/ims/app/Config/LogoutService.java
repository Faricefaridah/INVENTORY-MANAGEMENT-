package com.ims.app.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LogoutService extends OncePerRequestFilter {
  private JwtService jwtService;
  private  UserDetailsService userDetailsService;
  private TokenRepository tokenRepository;
  //private TokenService tokenService;

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain) throws ServletException, IOException {
    if (request.getServletPath().contains("/api/v1/auth")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userNationalId;

    System.out.println("Auth Header:-->" + authHeader);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      System.out.println("No Token....>>>");
      filterChain.doFilter(request, response);
      return;
    }

    jwt = authHeader.substring(7);
    try {
      userNationalId = jwtService.extractUsername(jwt);
    } catch (Exception e) {
      ApiResponse<?> apiResponse = new ApiResponse<>("Invalid access token", null, HttpStatus.UNAUTHORIZED.value());
      String json = new ObjectMapper().writeValueAsString(apiResponse);
      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write(json);
      return;
    }

    System.out.println("The Authenticated User: " + userNationalId);
    if (userNationalId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userNationalId);

      var isTokenValid = tokenRepository.findByToken(jwt)
              .map(t -> !t.isTokenExpired() && !t.isRevoked())
              .orElse(false);
      if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      } else {
        ApiResponse<?> apiResponse = new ApiResponse<>("Token is expired or revoked", null,
                HttpStatus.UNAUTHORIZED.value());
        String json = new ObjectMapper().writeValueAsString(apiResponse);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(json);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }
}
