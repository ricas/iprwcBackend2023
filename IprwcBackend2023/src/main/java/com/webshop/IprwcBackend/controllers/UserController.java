package com.webshop.IprwcBackend.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.IprwcBackend.dtos.RegisterUserDTO;
import com.webshop.IprwcBackend.services.OrderService;
import com.webshop.IprwcBackend.dtos.UserDTO;
import com.webshop.IprwcBackend.models.Order;
import com.webshop.IprwcBackend.models.Role;
import com.webshop.IprwcBackend.models.User;
import com.webshop.IprwcBackend.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "https://iprwcfrontend2023.herokuapp.com")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllUserOrders() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.getUser(username);
        log.info(user.getName());
        log.info(user.getEmail());
        log.info("hoi" + user.getId());
        return ResponseEntity.ok().body(orderService.getOrdersByUserId(user.getId()));
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@ModelAttribute User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());

                String SECRET_KEY = "7134743777217A25432A462D4A404E635266556A586E3272357538782F413F44";
                Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else throw new RuntimeException("Refresh token not found");
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUser (@ModelAttribute UserDTO userDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());

        user = this.userService.saveUser(user);

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/profile").toUriString());
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/profile/{name}")
    public ResponseEntity<User> updateUser (@ModelAttribute UserDTO userDTO, @PathVariable("name") String name) {
        User user = userService.getUser(name);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());

        user = this.userService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> profile () {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.getUser(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser (@RequestBody RegisterUserDTO userDTO) {

        if (userService.getUser(userDTO.getUsername())!=null && userDTO.getPassword()!=null){
        return ResponseEntity.status(CONFLICT).build();
        }
        User user = userService.saveUser(new User(
                null,
                userDTO.getName(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                new ArrayList<>()
        ));

        userService.addRoleToUser(user.getUsername(), "ROLE_USER");

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Long> getUserIdByName (@PathVariable("name") String name) {

        User user = userService.getUser(name);

        if (user==null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

}

