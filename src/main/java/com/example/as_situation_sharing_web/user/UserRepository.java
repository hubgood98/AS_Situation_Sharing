package com.example.as_situation_sharing_web.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<UserData> findByUsername(String username);
}
