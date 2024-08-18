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
    void testBoardList() {
        for(int i=1;i<=200;i++)
        {
            String subject = String.format("테스트 데이터 :[%03d]",i);
            String content = "내용은 없습니다";
            this.questionService.create(subject,content,null);
        }
    }

}
