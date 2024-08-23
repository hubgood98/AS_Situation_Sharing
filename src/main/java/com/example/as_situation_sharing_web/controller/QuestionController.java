package com.example.as_situation_sharing_web.controller;

import com.example.as_situation_sharing_web.domain.Question;
import com.example.as_situation_sharing_web.service.QuestionService;
import com.example.as_situation_sharing_web.domain.UserData;
import com.example.as_situation_sharing_web.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model,@RequestParam(value = "page", defaultValue = "0") int page)
    {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging",paging);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id)
    {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question",question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate() {

        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid @RequestParam("subject") String subject,
                                 @RequestParam("content") String content, Principal principal) {

        UserData userData = this.userService.getUser(principal.getName());
        this.questionService.create(subject, content,userData);
        return "redirect:/question/list";
    }

}
