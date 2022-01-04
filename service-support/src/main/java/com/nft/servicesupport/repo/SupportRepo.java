package com.nft.servicesupport.repo;
import com.nft.servicesupport.repo.model.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SupportRepo extends JpaRepository<Support, Long> {
}
