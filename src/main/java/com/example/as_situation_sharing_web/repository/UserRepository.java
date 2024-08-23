package com.example.as_situation_sharing_web.repository;

import com.example.as_situation_sharing_web.domain.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<UserData> findByUsername(String username);
    Optional<UserData> findByEmail(String email);

}
