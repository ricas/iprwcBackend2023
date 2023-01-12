package com.webshop.IprwcBackend.controllers;

import com.webshop.IprwcBackend.dtos.RoleToUserDTO;
import com.webshop.IprwcBackend.models.Role;
import com.webshop.IprwcBackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/role")
@CrossOrigin(origins = "https://iprwcfrontend2023.herokuapp.com")
@RequiredArgsConstructor
public class RoleController {
    private final UserService userService;

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserDTO roleToUserDTO) {
        userService.addRoleToUser(roleToUserDTO.getUsername(), roleToUserDTO.getRoleName());
        return ResponseEntity.ok().build();
    }
}
