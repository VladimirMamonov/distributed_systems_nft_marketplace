package com.nft.serviceusers.api;

import com.nft.serviceusers.api.dto.OrderDTO;
import com.nft.serviceusers.api.dto.SupportDTO;
import com.nft.serviceusers.api.dto.UserDTO;
import com.nft.serviceusers.repo.model.User;
import com.nft.serviceusers.service.UserService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> index() {
        final List<User> users = userService.fetchAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable long id) {
        try {
            final User user = userService.fetchById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDTO>> showOrdersById(@PathVariable long id) {
        try {
            final List<OrderDTO> orders = userService.getUserOrders(id);

            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/supports")
    public ResponseEntity<List<SupportDTO>> showSupportsById(@PathVariable long id) {
        try {
            final List<SupportDTO> supports = userService.getUserSupports(id);

            return ResponseEntity.ok(supports);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<JSONObject> create(@RequestBody UserDTO user) {
        final String username = user.getUsername();
        final String password = user.getPassword();
        final String email = user.getEmail();
        final String phone = user.getPhone();
        try {
            final long id = userService.create(username, password, email, phone);
            final String location = String.format("/users/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (IllegalArgumentException e) {
            JSONObject response = new JSONObject();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody UserDTO user) {
        final String username = user.getUsername();
        final String password = user.getPassword();
        final String email = user.getEmail();
        final String phone = user.getPhone();
        try {
            userService.update(id, username, password, email, phone);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}