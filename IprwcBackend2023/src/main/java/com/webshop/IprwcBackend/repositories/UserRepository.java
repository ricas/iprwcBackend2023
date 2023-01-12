package com.webshop.IprwcBackend.repositories;

import com.webshop.IprwcBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
