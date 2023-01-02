package com.muates.notificationservice.repository;

import com.muates.notificationservice.model.entity.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailActivationRepository extends JpaRepository<EmailToken, Long> {
}
