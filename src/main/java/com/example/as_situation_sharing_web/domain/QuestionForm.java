package com.example.as_situation_sharing_web.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionForm {

    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
}
