package com.example.as_situation_sharing_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String root()
    {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home()
    {
        return "home";
    }

}
