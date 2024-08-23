package com.example.as_situation_sharing_web.dto.user;

import com.example.as_situation_sharing_web.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

//클라이언트가 서버로 사용자 정보를 전송할때 사용됨, 예시) 회원가입 및 사용자 정보 업데이트
@Getter
@Builder
public class UserRequest {
    @NotBlank(message = "UserID는 필수 항목입니다.")
    @Size(min = 4, max = 20, message = "UserID는 4자 이상 20자 이하여야 합니다.")
    private final String userid;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private final String password;

    @NotBlank(message = "사용자 이름은 필수 항목입니다.")
    @Size(max = 50, message = "사용자 이름은 최대 50자까지 가능합니다.")
    private final String username;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "유효한 이메일 형식을 입력해주세요.")
    private final String email;

    @Size(max = 15, message = "전화번호는 최대 15자까지 가능합니다.")
    private final String phoneNumber;

    @Size(max = 255, message = "자기소개는 최대 255자까지 가능합니다.")
    private final String bio;

    @NotBlank(message = "사용자 역할은 필수 항목입니다.")
    private final UserRole role; // "CUSTOMER" 또는 "TECHNICIAN"
}