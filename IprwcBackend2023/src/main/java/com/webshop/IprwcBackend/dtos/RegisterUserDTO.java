package com.webshop.IprwcBackend.dtos;

import lombok.Data;

@Data
public class RegisterUserDTO {
    private String name;
    private String email;
    private String username;
    private String password;
}
