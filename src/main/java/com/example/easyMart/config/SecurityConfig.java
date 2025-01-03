package com.example.easyMart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.easyMart.Jwt.JWTAuthFilter;
import com.example.easyMart.Service.MyUserDetailsService;
import com.example.easyMart.Service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService ourUserDetailsService;
    @Autowired
    private JWTAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        // Public access to login
                        .requestMatchers("/api/login").permitAll()
                        // ProductController access configuration
                        .requestMatchers("/uploads/**").permitAll()  // Allow access to uploads folder
                        .requestMatchers("/api/products").permitAll() // GET access for listing products
                        .requestMatchers("/api/categories").permitAll()
                        .requestMatchers("/api/categories/**").permitAll()
                        .requestMatchers("/api/products/{id}").permitAll() // GET access for fetching product by ID
                        .requestMatchers("/api/products/create").hasAuthority("ADMIN") // Only admins can create products
                        .requestMatchers("/api/products/{id}").hasAuthority("ADMIN") // Only admins can update or delete
                        // UserController access configuration
                        .requestMatchers("/api/products/filters/{categoryName}").permitAll()
                        .requestMatchers("/api/products/filtered").permitAll()
                        .requestMatchers("/api/products/category/{categoryName}").permitAll()
                        .requestMatchers("/api/signup").permitAll() // Public access to signup
                        .requestMatchers("/api/updateUser").hasAuthority("USER") // Users can update their profile
                        .requestMatchers("/api/address").hasAuthority("USER") // Users can add an address
                        .requestMatchers("/api/addresses").hasAuthority("USER") // Users can view their addresses
                        .requestMatchers("/api/address/{addressId}").hasAuthority("USER") // Users can delete their address
                        // Default: all other requests must be authenticated
                        .anyRequest().authenticated())
                .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
                );
        return httpSecurity.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(ourUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}
