package com.example.as_situation_sharing_web.dto.user;

import com.example.as_situation_sharing_web.user.UserRole;
import lombok.Builder;
import lombok.Getter;

// 서버가 클라이언트에게 사용자 정보를 전달할 때 사용한다. 예를 들어, 사용자 프로필 조회 시에 활용
@Getter
@Builder
public class UserResponse {
    private final Long id;
    private final String userid;
    private final String username;
    private final String email;
    private final String phoneNumber;
    private final String bio;
    private final UserRole role;
}
