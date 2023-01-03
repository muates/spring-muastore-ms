package com.muates.notificationservice.repository;

import com.muates.notificationservice.model.entity.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailActivationRepository extends JpaRepository<EmailToken, Long> {
    Optional<EmailToken> findByUserId(Long userId);
}
