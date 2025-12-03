package com.gniot.parkinglot.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    public static final String SECRET = "5367566859703373367639792F423F452848284D6251655468576D5A71347437";

    public String generateToken(String email, Long id, String role, Long parkingLotId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = now.plusMinutes(30);

        JwtToken jwtToken = JwtToken.builder()
                .id(id)
                .role(role)
                .parkingLotId(parkingLotId)
                .createTime(now)
                .expireTime(expiry)
                .build();

        return createToken(jwtToken, email);
    }

    private String createToken(JwtToken jwtToken, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", jwtToken.getId());
        claims.put("role", jwtToken.getRole());
        claims.put("parkingLotId", jwtToken.getParkingLotId());
        claims.put("createTime", jwtToken.getCreateTime().toString());
        claims.put("expireTime", jwtToken.getExpireTime().toString());

        Date issuedAt = Date.from(jwtToken.getCreateTime()
                .atZone(ZoneId.systemDefault()).toInstant());
        Date expiration = Date.from(jwtToken.getExpireTime()
                .atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }
    public Long extractParkingLotId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("parkingLotId", Long.class);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public JwtToken extractJwtToken(String token) {
        Claims claims = extractAllClaims(token);

        Long id = null;
        Object idObj = claims.get("id");
        if (idObj instanceof Integer i) {
            id = i.longValue();
        } else if (idObj instanceof Long l) {
            id = l;
        }

        String role = (String) claims.get("role");
        Long parkingLotId = (Long) claims.get("parkingLotId");

        LocalDateTime createTime = LocalDateTime.ofInstant(
                claims.getIssuedAt().toInstant(), ZoneId.systemDefault());

        LocalDateTime expireTime = LocalDateTime.ofInstant(
                claims.getExpiration().toInstant(), ZoneId.systemDefault());

        return JwtToken.builder()
                .id(id)
                .role(role)
                .parkingLotId(parkingLotId)
                .createTime(createTime)
                .expireTime(expireTime)
                .build();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}