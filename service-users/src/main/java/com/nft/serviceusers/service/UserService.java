package com.nft.serviceusers.service;

import com.nft.serviceusers.api.dto.OrderDTO;
import com.nft.serviceusers.api.dto.SupportDTO;
import com.nft.serviceusers.repo.UserRepo;
import com.nft.serviceusers.repo.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class UserService {
    private final UserRepo userRepo;
    private final String orderURL = "http://service-orders:8081/orders/";
    private final String supportURL = "http://service-support:8082/supports/";
    public List<User> fetchAll() {
        return userRepo.findAll();
    }

    public User fetchById(long id) throws IllegalArgumentException {
        final Optional<User> byId = userRepo.findById(id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("User with id = %d was not found", id));
        return byId.get();
    }
    public long create(String username, String password, String email, String phone) {
        final User user = new User(username, password, email, phone);
        final User save = userRepo.save(user);
        return save.getId();
    }
    public List<OrderDTO> getUserOrders(long id) {
        final Optional<User> byId = userRepo.findById(id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("User with id = %d was not found", id));
        final User user = byId.get();
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<List<OrderDTO>> response = restTemplate.exchange(orderURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<OrderDTO>>() {});
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException(String.format("User with id = %d has no orders", user.getId()));
        }
        // response содержит JSON со всеми заказами, нужно отфильтровать те, у которых user_id = id
        List<OrderDTO> user_orders = new ArrayList<OrderDTO>();
        for (OrderDTO order : Objects.requireNonNull(response.getBody())) {
            if (order.getUser_id() == user.getId()) {
                user_orders.add(order);
            }
        }
        return user_orders;
    }
    public List<SupportDTO> getUserSupports(long id) {
        final Optional<User> byId = userRepo.findById(id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("Order with id = %d was not found", id));
        final User user = byId.get();
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<List<SupportDTO>> response = restTemplate.exchange(supportURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SupportDTO>>() {});
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException(String.format("User with id = %d has no supports", user.getId()));
        }
        List<SupportDTO> user_supports = new ArrayList<SupportDTO>();
        for (SupportDTO support : Objects.requireNonNull(response.getBody())) {
            if (support.getUser_id() == user.getId()) {
                user_supports.add(support);
            }
        }
        return user_supports;
    }
    public void update(long id, String username, String password, String email, String phone)
            throws IllegalArgumentException {
        final Optional<User> byId = userRepo.findById(id);
        final User user = byId.get();
        if(username != null && !username.isEmpty()) user.setUsername(username);
        if(password != null && !password.isEmpty()) user.setPassword(password);
        if(email != null && !email.isEmpty()) user.setEmail(email);
        if(phone != null && !phone.isEmpty()) user.setPhone(phone);
        userRepo.save(user);
    }

    public void delete(long id) {
        userRepo.deleteById(id);
    }
}
