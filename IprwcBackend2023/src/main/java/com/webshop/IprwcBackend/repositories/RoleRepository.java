package com.webshop.IprwcBackend.repositories;

import com.webshop.IprwcBackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
