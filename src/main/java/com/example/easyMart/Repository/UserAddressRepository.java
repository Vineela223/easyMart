
package com.example.easyMart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.easyMart.Entity.User;
import com.example.easyMart.Entity.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    // Additional custom query methods (if needed) can be defined here
	
    List<UserAddress> findByemailId(String emailId);

}
