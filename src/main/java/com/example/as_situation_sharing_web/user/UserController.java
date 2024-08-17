package com.example.as_situation_sharing_web.user;

import com.example.as_situation_sharing_web.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("userCreateForm",new UserCreateForm());

        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "signup_form";
        }

        if(!userCreateForm.isPasswordConfirmed())
        {
            bindingResult.rejectValue("password2","passwordInCorrect","2개의 비밀번호가 일치하지 않습니다.");

            return "signup_form";
        }

        try{
            userService.createUser(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.rejectValue("signupFailed","이미 등록된 사용자입니다.");

            return "signup_form";
        }catch (Exception e)
        {
            e.printStackTrace();
            bindingResult.rejectValue("signupFailed",e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/checkUsername")
    @ResponseBody
    public Map<String,Boolean> checkUsername(@RequestParam String username){
        boolean exists = userService.isUsernameTaken(username);

        Map<String,Boolean> response = new HashMap<>();
        response.put("exists",exists);

        return response;
    }

    @GetMapping("/checkEmail")
    @ResponseBody
    public Map<String, Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userService.isEmailTaken(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}
