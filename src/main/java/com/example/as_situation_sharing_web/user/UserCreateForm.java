package com.example.as_situation_sharing_web.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateForm {
    @Size(min =3, max =25)
    @NotEmpty(message = "사용자 Id는 필수 항목입니다.")
    private String username;

    private String password1;
    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수 항목입니다")
    @Email //해당형식이 이메일인지 확인
    private String email;
}
