package com.example.as_situation_sharing_web;

import com.example.as_situation_sharing_web.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AsSituationSharingWebApplicationTests {

    @Autowired
    private QuestionService questionService;

    @Test
    void contextLoads() {
    }

}
