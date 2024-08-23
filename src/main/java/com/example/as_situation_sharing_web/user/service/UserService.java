package com.example.as_situation_sharing_web.user.service;

import com.example.as_situation_sharing_web.exception.DataNotFoundException;
import com.example.as_situation_sharing_web.domain.UserData;
import com.example.as_situation_sharing_web.repository.UserRepository;
import com.example.as_situation_sharing_web.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserData createUser(String username, String email, String password, UserRole role, String phoneNumber, String bio) {

        UserData user = UserData.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .phone_number(phoneNumber)
                .bio(bio)
                .build();

        this.userRepository.save(user);

        return user;
    }

    //사용자 이름으로 UserData를 조회할 수 있는 메서드
    public UserData getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    public UserData getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
    }


    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }
}
