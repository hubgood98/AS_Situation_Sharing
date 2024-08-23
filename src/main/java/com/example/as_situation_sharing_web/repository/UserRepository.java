package com.example.as_situation_sharing_web.repository;

import com.example.as_situation_sharing_web.domain.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {

    boolean existsByUserid(String userid);
    boolean existsByEmail(String email);
    Optional<UserData> findByUserid(String userid);
    Optional<UserData> findByEmail(String email);
}