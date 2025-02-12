package com.pdrw.pdrw.subscription.repository;

import com.pdrw.pdrw.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    @Query(value = "SELECT count(id) FROM subscription WHERE user_id = :userId", nativeQuery = true)
    Optional<Integer> countSubscriptionsByUserId(UUID userId);

    @Query(value = "SELECT email FROM subscription WHERE user_id = :userId", nativeQuery = true)
    List<String> findEmailsByUserId(UUID userId);

    List<Subscription> findByActiveAndCreateDateBefore(Boolean active, LocalDateTime createDate);

    Optional<Subscription> findByEmail(String email);
}