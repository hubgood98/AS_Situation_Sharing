package com.example.as_situation_sharing_web.domain;

import com.example.as_situation_sharing_web.user.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userid; //로그인시 사용할 id
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;  // customer 또는 technician

    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone_number;
    private String bio; //자기소개칸

}
