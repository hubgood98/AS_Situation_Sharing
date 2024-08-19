package com.example.as_situation_sharing_web.controller;

import com.example.as_situation_sharing_web.domain.Question;
import com.example.as_situation_sharing_web.service.AnswerService;
import com.example.as_situation_sharing_web.service.QuestionService;
import com.example.as_situation_sharing_web.user.UserData;
import com.example.as_situation_sharing_web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable Integer id,
                               @RequestParam(value="content") String content, Model model, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        UserData userdata = this.userService.getUser(principal.getName());



        this.answerService.create(question, content,userdata);
        return String.format("redirect:/question/detail/%s",id);
    }

}
