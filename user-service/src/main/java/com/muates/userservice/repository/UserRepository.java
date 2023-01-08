package com.muates.userservice.repository;

import com.muates.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);

    @Query(
            value = "SELECT * FROM users u " +
                    "WHERE u.is_enabled = false " +
                    "AND u.created_date < CURRENT_DATE LIMIT 10;",
            nativeQuery = true
    )
    List<User> findAllExpiredUserByCreatedDate();
}
