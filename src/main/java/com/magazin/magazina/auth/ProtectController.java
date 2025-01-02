package com.magazin.magazina.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/protected")
public class ProtectController {

    private final String secretKey = "VGhpcyBpcyBhIHZlcnkgc2VjdXJlIGtleSBvZiAyNTYtYml0cw==";

    @GetMapping("/get-role")
    public ResponseEntity<String> getRole(
            @RequestHeader("Authorization") Optional<String> tokenHeader) {

        if (tokenHeader.isEmpty() || !tokenHeader.get().startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }

        String token = tokenHeader.get().substring(7);

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String userRole = claims.get("role", String.class);

            if (userRole == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Role not found in token");
            }

            return ResponseEntity.ok(userRole);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
