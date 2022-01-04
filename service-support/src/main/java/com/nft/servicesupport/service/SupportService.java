package com.nft.servicesupport.service;
import com.nft.servicesupport.repo.SupportRepo;
import com.nft.servicesupport.repo.model.Support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class SupportService {
    private final SupportRepo supportRepo;
    public List<Support> fetchAll() {
        return supportRepo.findAll();
    }

    public Support fetchById(long id) throws IllegalArgumentException {
        final Optional<Support> byId = supportRepo.findById(id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("Support with id = %d was not found", id));
        return byId.get();
    }
    public long create(long user_id, long order_id, String feedback) {
        final Support support = new Support(user_id, order_id, feedback);
        final Support save = supportRepo.save(support);
        return save.getId();
    }

    public void update(long id, String feedback)
            throws IllegalArgumentException {
        final Optional<Support> byId = supportRepo.findById(id);
        final Support Support = byId.get();
        if(feedback != null && !feedback.isEmpty()) Support.setFeedback(feedback);
        supportRepo.save(Support);
    }

    public void delete(long id) {
        supportRepo.deleteById(id);
    }
}
