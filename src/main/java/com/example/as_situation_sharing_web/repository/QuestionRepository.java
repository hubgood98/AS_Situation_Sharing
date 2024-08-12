package com.example.as_situation_sharing_web.repository;

import com.example.as_situation_sharing_web.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubjectAndContent(String subject, String content);
}
