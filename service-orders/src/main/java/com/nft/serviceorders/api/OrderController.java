package com.nft.serviceorders.api;

import com.nft.serviceorders.api.dto.OrderDTO;
import com.nft.serviceorders.api.dto.SupportDTO;
import com.nft.serviceorders.repo.model.Order;
import com.nft.serviceorders.service.OrderService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> index() {
        final List<Order> orders = orderService.fetchAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> show(@PathVariable long id) {
        try {
            final Order order = orderService.fetchById(id);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/supports")
    public ResponseEntity<List<SupportDTO>> showSupportsById(@PathVariable long id) {
        try {
            final List<SupportDTO> supports = orderService.getOrderSupports(id);

            return ResponseEntity.ok(supports);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<JSONObject> create(@RequestBody OrderDTO order) {
        final long user_id = order.getUser_id();
        final String description = order.getDescription();
        final float price = order.getPrice();
        final String nft_url = order.getNft_url();
        try {
            final long id = orderService.create(user_id, description, price, nft_url);
            final String location = String.format("/orders/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (IllegalArgumentException e) {
            JSONObject response = new JSONObject();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody OrderDTO order) {
        final String description = order.getDescription();
        final float price = order.getPrice();
        final String nft_url = order.getNft_url();
        try {
            orderService.update(id, description, price, nft_url);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}