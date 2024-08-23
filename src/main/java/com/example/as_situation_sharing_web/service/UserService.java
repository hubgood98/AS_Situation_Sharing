package com.example.as_situation_sharing_web.service;

import com.example.as_situation_sharing_web.exception.DataNotFoundException;
import com.example.as_situation_sharing_web.domain.UserData;
import com.example.as_situation_sharing_web.repository.UserRepository;
import com.example.as_situation_sharing_web.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserData createUser(String userid, String password, UserRole role, String username, String email, String phoneNumber, String bio) {
        UserData user = UserData.builder()
                .userid(userid)
                .password(passwordEncoder.encode(password))
                .role(role)
                .username(username)
                .email(email)
                .phone_number(phoneNumber)
                .bio(bio)
                .build();

        return userRepository.save(user);
    }

    //사용자 ID로 UserData를 조회할 수 있는 메서드
    public UserData getUserByUserId(String userid) {
        return userRepository.findByUserid(userid)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    public UserData getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
    }


    public boolean isUserIdTaken(String userid) {
        return userRepository.existsByUserid(userid);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }
}
