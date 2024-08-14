package com.example.as_situation_sharing_web.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
//답변 클래스
public class Answer {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

@Column(columnDefinition = "Text")
private String content;

private LocalDateTime createDate;

@ManyToOne
private Question question;

}
