package com.example.as_situation_sharing_web.service;

import com.example.as_situation_sharing_web.domain.Answer;
import com.example.as_situation_sharing_web.domain.Question;
import com.example.as_situation_sharing_web.repository.AnswerRepository;
import com.example.as_situation_sharing_web.user.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content, UserData author){
        Answer answer = Answer.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .question(question)
                .author(author)
                .build();

        this.answerRepository.save(answer);
    }
}
