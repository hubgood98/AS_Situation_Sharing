package com.example.as_situation_sharing_web.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserData, Long> {

    boolean existsByUsername(String username);
}
