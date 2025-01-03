package com.example.easyMart.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.easyMart.Entity.User;
import com.example.easyMart.dto.LoginResponse;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByemailId(String emailId);

    
    
}
