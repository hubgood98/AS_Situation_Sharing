package com.example.as_situation_sharing_web.user.service;

import com.example.as_situation_sharing_web.exception.DataNotFoundException;
import com.example.as_situation_sharing_web.user.UserData;
import com.example.as_situation_sharing_web.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserData createUser(String username, String email, String password) {

        UserData user = UserData.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        this.userRepository.save(user);

        return user;
    }

    public UserData getUser(String username) {
        Optional<UserData> userData = this.userRepository.findByUsername(username);
        if(userData.isPresent()) {
            return userData.get();
        }else{
            throw new DataNotFoundException("user not fount");
        }
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }
}
