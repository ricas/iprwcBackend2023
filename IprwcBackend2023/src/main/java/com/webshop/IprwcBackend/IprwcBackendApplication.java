package com.webshop.IprwcBackend;

import com.webshop.IprwcBackend.services.ProductService;
import com.webshop.IprwcBackend.models.Product;
import com.webshop.IprwcBackend.models.Role;
import com.webshop.IprwcBackend.models.User;
import com.webshop.IprwcBackend.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@SpringBootApplication
@CrossOrigin(origins = "https://iprwcfrontend2023.herokuapp.com")
public class IprwcBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(IprwcBackendApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("https://iprwcfrontend2023.herokuapp.com");
            }
        };
    }

    @Bean
    CommandLineRunner run(UserService userService, ProductService productService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));

            userService.saveUser(new User(null, "Rick", "rick", "0123", "rick@gmail.com", new ArrayList<>()));
            userService.saveUser(new User(null, "test", "test", "test", "test@gmail.com", new ArrayList<>()));
            userService.saveUser(new User(null, "user", "user", "user", "user@gmail.com", new ArrayList<>()));

            userService.addRoleToUser("rick", "ROLE_ADMIN");
            userService.addRoleToUser("rick", "ROLE_USER");
            userService.addRoleToUser("test", "ROLE_USER");
            userService.addRoleToUser("user", "ROLE_USER");


            productService.saveProduct(new Product(null, "Product 1", "All black shoes", 60.00, "https://images.unsplash.com/photo-1544327415-cfb77383dabc?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8MXwxMTQxNzgwMXx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"));
            productService.saveProduct(new Product(null, "Product 2", "Perfect for going on a run", 100.00, "https://images.unsplash.com/photo-1603990600119-3c3b4f80468d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8MnwxMTQxNzgwMXx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"));
            productService.saveProduct(new Product(null, "Product 3", "Simple and grey", 85.00, "https://images.unsplash.com/photo-1556731329-62da0b1ae213?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8M3wxMTQxNzgwMXx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"));
            productService.saveProduct(new Product(null, "Product 4", "All black shoes, but expensive", 400.00, "https://images.unsplash.com/photo-1612723683658-89814a429607?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8NHwxMTQxNzgwMXx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"));
            productService.saveProduct(new Product(null, "Product 5", "Water resistant as you can see", 133.00, "https://images.unsplash.com/photo-1562424995-2efe650421dd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8NXwxMTQxNzgwMXx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"));
            productService.saveProduct(new Product(null, "Product 6", "Classy and clean", 209.00, "https://images.unsplash.com/photo-1612821745127-53855be9cbd1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8N3wxMTQxNzgwMXx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"));
            productService.saveProduct(new Product(null, "Product 7", "An absolute masterpiece", 242.00, "https://images.unsplash.com/photo-1597045566677-8cf032ed6634?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8OHwxMTQxNzgwMXx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"));
            productService.saveProduct(new Product(null, "Product 8", "Quite the flashy ones", 213.00, "https://images.unsplash.com/photo-1588099768550-4014589e03e0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8OXwxMTQxNzgwMXx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"));
            productService.saveProduct(new Product(null, "Product 9", "Can't go wrong with these", 125.00, "https://images.unsplash.com/photo-1543508282-6319a3e2621f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8MTB8MTE0MTc4MDF8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=60"));
            productService.saveProduct(new Product(null, "Product 10", "The classics", 104.00, "https://images.unsplash.com/photo-1512374382149-233c42b6a83b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8NXw4NjY0NjQxfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));

        };
    }
}
