package com.magazin.magazina.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(
        origins = "http://localhost:3000", // Frontend URL
        allowedHeaders = "*", // Allow all headers
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS}, // Allowed methods
        allowCredentials = "true" // Allow credentials like cookies
)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity < AuthenticationResponse > register(
        @RequestBody RegisterRequest request
    ) {
        //return ResponseEntity.ok(service.register(request));
        // Retrieve the token using getToken()
        AuthenticationResponse authResponse = service.register(request);
        System.out.println(authResponse);
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", authResponse.getToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("None")
                .path("/")
                .maxAge(14 * 24 * 60 * 60*100)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(authResponse);
    }



    /*@PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse authResponse =  service.authenticate(request);

        System.out.println(authResponse);
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", authResponse.getToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("None")
                .path("/")
                .maxAge(14 * 24 * 60 * 60*100)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(authResponse);
    }*/


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authResponse = service.authenticate(request);

        if (authResponse == null) {
            return ResponseEntity.status(401).body(null);  // Unauthorized if authentication fails
        }

        ResponseCookie jwtCookie = ResponseCookie.from("jwt", authResponse.getToken())
                .httpOnly(true)
                .secure(false) // Set to true in production
                .sameSite("None")
                .path("/")
                .maxAge(14 * 24 * 60 * 60 * 100)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(authResponse);
    }

    // Logout method
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Clear the JWT cookie by setting its max age to 0 (immediate expiration)
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) // Set to true in production
                .sameSite("None")
                .path("/")
                .maxAge(0) // Immediately expire the cookie
                .build();

        // Return a response that clears the cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();
    }

}
