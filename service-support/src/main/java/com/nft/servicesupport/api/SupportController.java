package com.nft.servicesupport.api;
import com.nft.servicesupport.api.dto.SupportDTO;
import com.nft.servicesupport.repo.model.Support;
import com.nft.servicesupport.service.SupportService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/supports")
public class SupportController {
    private final SupportService supportService;

    @GetMapping
    public ResponseEntity<List<Support>> index() {
        final List<Support> supports = supportService.fetchAll();
        return ResponseEntity.ok(supports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Support> show(@PathVariable long id) {
        try {
            final Support support = supportService.fetchById(id);
            return ResponseEntity.ok(support);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<JSONObject> create(@RequestBody SupportDTO support) {
        final long user_id = support.getUser_id();
        final long order_id = support.getOrder_id();
        final String feedback = support.getFeedback();
        try {
            final long id = supportService.create(user_id, order_id, feedback);
            final String location = String.format("/supports/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (IllegalArgumentException e) {
            JSONObject response = new JSONObject();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody SupportDTO support) {
        final String feedback = support.getFeedback();
        try {
            supportService.update(id, feedback);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        supportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
