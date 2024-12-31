package com.magazin.magazina.services;
import com.magazin.magazina.config.JwtService;
import com.magazin.magazina.models.Product;
import com.magazin.magazina.models.Stock;
import com.magazin.magazina.models.User;
import com.magazin.magazina.repositories.ProductRepository;
import com.magazin.magazina.repositories.StockRepository;
import com.magazin.magazina.repositories.UserRepository;
//import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;


    @Autowired
    private JwtService jwtService; // Assuming JwtUtils is a service for decoding the token

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Stock updateStock (Integer id, Stock updatedStock, String token) {
        // Extract the user ID from the token (used for createdBy/updatedBy)
        String userId = jwtService.getUserIdFromToken(token);
        System.out.println(userId);
        Optional<Stock> existingStockOptional = stockRepository.findById(id);

        if (existingStockOptional.isPresent()) {
            Stock existingStock = existingStockOptional.get();

            // Set the 'updatedBy' field
            //existingStock.setUpdatedBy(userId);

            // Update the necessary fields
            existingStock.setQuantity(updatedStock.getQuantity());
            existingStock.setSku(updatedStock.getSku());
            existingStock.setReorderLevel(updatedStock.getReorderLevel());
            existingStock.setStatus(updatedStock.getStatus());
            existingStock.setProduct(updatedStock.getProduct());

            return stockRepository.save(existingStock); // Save the updated stock
        } else {
            throw new RuntimeException("Stock with ID " + id + " not found.");
        }
    }

    //@Transactional
    // If creating a new stock, set the createdBy field as well
    public Stock createStock(Stock newStock, String token) {
        System.out.println("\n\n\n\n\n\n\n\n");
        String userId = jwtService.getUserIdFromToken(token);
        System.out.println(userId);

        System.out.println("\n\n\n\nUser: " + userId);
        Integer userIdx = Integer.parseInt(userId);
        System.out.println(newStock.getProduct());
        // Fetch the Product entity based on the productId passed in the request
        Product product = productRepository.findById(newStock.getProduct().getId()) // Assuming newStock.getProduct() has only the id set
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + newStock.getProduct().getId()));

        Stock newStock1 = new Stock();

        System.out.println(newStock);

        // Fetch the user entity from the database
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userIdx));
        newStock1.setCreatedBy(user);
        //Hibernate.initialize(newStock1.getCreatedBy()); // Force initialization
        newStock1.setCreatedAt(LocalDateTime.now());
        newStock1.setQuantity(newStock.getQuantity());
        newStock1.setReorderLevel(newStock.getReorderLevel());
        newStock1.setProduct(newStock.getProduct());
        newStock1.setSku(newStock.getSku());
        newStock1.setStatus(newStock.getStatus());

        return stockRepository.save(newStock1); // Save the new stock
    }



    // Get all stocks
    public List<Stock> getAllStocks() {
        return stockRepository.findAll(Sort.by(Sort.Direction.ASC, "quantity"));
    }

    // Get a stock by its ID
    public Stock getStockById(Integer id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock with ID " + id + " not found."));
    }

    // Delete a stock
    public void deleteStock(Integer id) {
        Optional<Stock> existingStockOptional = stockRepository.findById(id);
        if (existingStockOptional.isPresent()) {
            stockRepository.deleteById(id);
        } else {
            throw new RuntimeException("Stock with ID " + id + " not found.");
        }
    }
}
