package com.example.as_situation_sharing_web.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateForm {

    @NotBlank(message = "사용자 ID는 필수 항목입니다.")
    @Size(min = 4, max = 20, message = "사용자 ID는 4자 이상 20자 이하여야 합니다.")
    private String userid;

    @NotBlank(message = "사용자 이름은 필수 항목입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password1;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String password2;

    @NotBlank(message = "이메일은 필수 항목입니다")
    @Email(message = "유효한 이메일 주소여야 합니다.")
    private String email;

    private String phoneNumber;

    @NotNull(message = "사용자 역할은 필수 항목입니다.")
    private UserRole role;

    private String bio;

    public boolean isPasswordConfirmed() {
        return password1 != null && password1.equals(password2);
    }


}
