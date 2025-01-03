package com.example.easyMart.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.easyMart.Entity.User;
import com.example.easyMart.Entity.UserAddress;
import com.example.easyMart.Jwt.JWTUtils;
import com.example.easyMart.Repository.UserAddressRepository;
import com.example.easyMart.Repository.UserRepository;
import com.example.easyMart.dto.LoginResponse;

@Service
public class UserService  {

	    @Autowired
	    private UserRepository userRepository;
	    
	    @Autowired
	    private UserAddressRepository userAddressRepository;
	    @Autowired
		private JWTUtils jwtUtils;
	    
	    @Autowired
	    private AuthenticationManager authenticationManager;
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder;


	    public User registerUser(User user) {
	    	
	    	
	    	String encodedPassword = passwordEncoder.encode(user.getPassword());
	        	user.setPassword(encodedPassword);
	         return userRepository.save(user);
	    
	    }

	    public boolean usernameExists(String username) {
	        return userRepository.findByemailId(username).isPresent();
	    }

	    public LoginResponse loginUser(User loginRequest) {
	    	LoginResponse response = new LoginResponse();

	    	try {
	            authenticationManager
	                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmailId(),
	                    		loginRequest.getPassword()));
	            var user = userRepository.findByemailId(loginRequest.getEmailId()).orElseThrow();
	            var jwt = jwtUtils.generateToken(user);
	            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
	            response.setStatusCode(200);
	            response.setToken(jwt);
	            response.setRole(user.getRole());
	            response.setExpirationTime("10Mins");
	            response.setFirstName(user.getFirstName());
	            response.setLastName(user.getLastName());
	            response.setEmailId(user.getEmailId());
	            response.setMessage("Successfully Logged In");

	        }catch (Exception e){
	            response.setStatusCode(500);
	            response.setMessage(e.getMessage());
	        }
	        return response;
	    }

		public User updateUser(User user) {
			User response= new User();
            Optional<User> userOptional = userRepository.findByemailId(user.getEmailId());
            if(userOptional.isPresent()) {
            	
                     response=userOptional.get();
			         response.setFirstName(user.getFirstName());
			         response.setLastName(user.getLastName());
			         // Check if password is present in the request
		                if (user.getPassword() != null && !user.getPassword().isEmpty()) {
		                    // Encode the password and update it
		                    response.setPassword(passwordEncoder.encode(user.getPassword()));
		                }

            }
          
            return userRepository.save(response);
		}

		 public UserAddress saveUserAddress(String emailId, UserAddress userAddress) {
		        userAddress.setEmailId(emailId); // Set the emailId directly
		        return userAddressRepository.save(userAddress);
		    }

		    public List<UserAddress> getUserAddresses(String emailId) {
		        return userAddressRepository.findByemailId(emailId);
		    }

			public void deleteUserAddress(Long addressId) {
				        // Check if the address exists before deleting
				        if (userAddressRepository.existsById(addressId)) {
				            userAddressRepository.deleteById(addressId);
				        } else {
				            throw new RuntimeException("Address not found with id: " + addressId);
				        }
				    }
		
	}

