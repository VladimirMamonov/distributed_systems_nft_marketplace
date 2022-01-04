package com.nft.serviceorders.service;

import com.nft.serviceorders.api.dto.SupportDTO;
import com.nft.serviceorders.repo.OrderRepo;
import com.nft.serviceorders.repo.model.Order;

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
public final class OrderService {
    private final OrderRepo orderRepo;
    private final String supportURL = "http://service-support:8082/supports/";
    public List<Order> fetchAll() {
        return orderRepo.findAll();
    }

    public Order fetchById(long id) throws IllegalArgumentException {
        final Optional<Order> byId = orderRepo.findById(id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("Order with id = %d was not found", id));
        return byId.get();
    }
    public long create(long user_id, String description, float price, String nft_url) {
        final Order order = new Order(user_id, description, price, nft_url);
        final Order save = orderRepo.save(order);
        return save.getId();
    }
    public List<SupportDTO> getOrderSupports(long id) {
        final Optional<Order> byId = orderRepo.findById(id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("Order with id = %d was not found", id));
        final Order order = byId.get();
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<List<SupportDTO>> response = restTemplate.exchange(supportURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SupportDTO>>() {});
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException(String.format("Order with id = %d has no supports", order.getId()));
        }
        List<SupportDTO> order_supports = new ArrayList<SupportDTO>();
        for (SupportDTO support : Objects.requireNonNull(response.getBody())) {
            if (support.getOrder_id() == order.getId()) {
                order_supports.add(support);
            }
        }
        return order_supports;
    }
    public void update(long id, String description, Float price, String nft_url)
            throws IllegalArgumentException {
        final Optional<Order> byId = orderRepo.findById(id);
        final Order order = byId.get();
        if(description != null && !description.isEmpty()) order.setDescription(description);
        if(price != null) order.setPrice(price);
        if(nft_url != null && !nft_url.isEmpty()) order.setNft_url(nft_url);
        orderRepo.save(order);
    }

    public void delete(long id) {
        orderRepo.deleteById(id);
    }
}
