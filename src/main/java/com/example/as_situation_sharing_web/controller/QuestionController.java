package com.example.as_situation_sharing_web.controller;

import com.example.as_situation_sharing_web.domain.Question;
import com.example.as_situation_sharing_web.domain.QuestionForm;
import com.example.as_situation_sharing_web.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model)
    {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList",questionList);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id)
    {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question",question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {

        return "redirect:/question/list";
    }



    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){
           if(bindingResult.hasErrors())
           {
               return "question_form";
           }
           this.questionService.create(questionForm.getSubject(),questionForm.getContent());

        return "redirect:/question/list";
    }

}
