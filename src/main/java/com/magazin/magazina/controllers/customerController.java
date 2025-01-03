package com.magazin.magazina.controllers;

import com.magazin.magazina.models.Customer;
import com.magazin.magazina.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(
        origins = "https://magazina.onrender.com", // Frontend URL
        allowedHeaders = "*", // Allow all headers
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS}, // Allowed methods
        allowCredentials = "true" // Allow credentials like cookies
)
@RestController
@RequestMapping("/api/v1/customers")
public class customerController {

    @Autowired
    private CustomerService customerService;

    // Customers
    @GetMapping()
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
