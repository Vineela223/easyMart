package com.example.easyMart.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.easyMart.Entity.User;
import com.example.easyMart.Entity.UserAddress;
import com.example.easyMart.Repository.UserRepository;
import com.example.easyMart.Service.UserService;
import com.example.easyMart.dto.LoginResponse;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // Signup Endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        System.out.println("API hit, received user: " + user);  // Log to confirm backend is hit

        if (userService.usernameExists(user.getEmailId())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        User newUser = userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody User  loginRequest) {
        //User user = userService.loginUser(loginRequest.getEmailId(), loginRequest.getPassword());
        return ResponseEntity.ok(userService.loginUser(loginRequest));

    }
    
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser( @RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }
    @PostMapping("/address")
    public UserAddress addUserAddress(@RequestParam String emailId, @RequestBody UserAddress userAddress) {
        System.out.println("API hit, received user: "+userAddress);  // Log to confirm backend is hit

        return userService.saveUserAddress(emailId, userAddress);
    }

    @GetMapping("/addresses")
    public List<UserAddress> getUserAddresses(@RequestParam String emailId) {
        return userService.getUserAddresses(emailId);
    }
    
    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<?> deleteUserAddresses(@PathVariable Long addressId) {
        userService.deleteUserAddress(addressId);
        return ResponseEntity.ok("Address deleted successfully");

    }
}