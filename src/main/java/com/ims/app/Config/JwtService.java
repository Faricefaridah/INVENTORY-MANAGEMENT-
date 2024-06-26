package com.ims.app.Config;//package com.ims.app.Config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Service
//public class JwtService {
//
//  @Value("${application.security.jwt.secret-key}")
//  private String secretKey;
//  @Value("${application.security.jwt.expiration}")
//  private long jwtExpiration;
//  @Value("${application.security.jwt.refresh-token.expiration}")
//  private long refreshExpiration;
//
//  public String extractUsername(String token) {
//    return extractClaim(token, Claims::getSubject);
//  }
//
//  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//    final Claims claims = extractAllClaims(token);
//    return claimsResolver.apply(claims);
//  }
//
//  public String generateToken(UserDetails userDetails) {
//    return generateToken(new HashMap<>(), userDetails);
//  }
//
//  public String generateToken(
//      Map<String, Object> extraClaims,
//      UserDetails userDetails
//  ) {
//    return buildToken(extraClaims, userDetails, jwtExpiration);
//  }
//
//  public String generateRefreshToken(
//      UserDetails userDetails
//  ) {
//    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
//  }
//
//  private String buildToken(
//          Map<String, Object> extraClaims,
//          UserDetails userDetails,
//          long expiration
//  ) {
//    return Jwts
//            .builder()
//            .setClaims(extraClaims)
//            .setSubject(userDetails.getUsername())
//            .setIssuedAt(new Date(System.currentTimeMillis()))
//            .setExpiration(new Date(System.currentTimeMillis() + expiration))
//            .signWith(SignatureAlgorithm.HS512, getSignInKey())
//            .compact();
//  }
//
//  public boolean isTokenValid(String token, UserDetails userDetails) {
//    System.out.println("Checking Token Validity):");
//    final String username = extractUsername(token);
//    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//  }
//
//  private boolean isTokenExpired(String token) {
//    return extractExpiration(token).before(new Date());
//  }
//
//  private Date extractExpiration(String token) {
//    return extractClaim(token, Claims::getExpiration);
//  }
//
//  private Claims extractAllClaims(String token) {
//    return Jwts
//        .parserBuilder()
//        .setSigningKey(getSignInKey())
//        .build()
//        .parseClaimsJws(token)
//        .getBody();
//  }
//
//  private Key getSignInKey() {
//    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//    return Keys.hmacShaKeyFor(keyBytes);
//  }
//}

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Service
public class JwtService {
  @Value("${application.security.jwt.secret-key}")
  private String secret;

  @Value("${application.security.jwt.expiration}")
  private Long expiration;

  public String generateToken(String username, String role) {
    return Jwts.builder()
            .setSubject(username)
            .claim("role", role)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
  }

  public boolean isTokenValid(String jwt, UserDetails userDetails) {
    return true;
  }

  public String extractUsername(String jwt) {
    return jwt;
  }
}

