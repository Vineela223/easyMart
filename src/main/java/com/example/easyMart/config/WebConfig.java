package com.example.easyMart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")  // Replace with your React app's URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
    

    	    @Override
    	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	        // Serve files from the 'uploads' folder outside the src directory
    	        registry.addResourceHandler("/uploads/**")
    	                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
    	    }


    }

