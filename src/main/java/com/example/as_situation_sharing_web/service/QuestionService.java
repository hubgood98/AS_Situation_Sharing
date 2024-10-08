package com.example.as_situation_sharing_web.service;

import com.example.as_situation_sharing_web.domain.Question;
import com.example.as_situation_sharing_web.exception.DataNotFoundException;
import com.example.as_situation_sharing_web.repository.QuestionRepository;
import com.example.as_situation_sharing_web.domain.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page)
    {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));

        return this.questionRepository.findAll(pageable);
    }

    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent())
        {
            return question.get();
        }else{
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, UserData user){
        Question q = Question.builder()
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now())
                .author(user)
                .build();

        this.questionRepository.save(q);
    }

}
