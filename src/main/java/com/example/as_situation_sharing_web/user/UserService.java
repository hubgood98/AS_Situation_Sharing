package com.example.as_situation_sharing_web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public boolean isUsernameTaken(String username) {
        return this.userRepository.existsByUsername(username);
    }
}
