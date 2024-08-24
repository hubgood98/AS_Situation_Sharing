package com.example.as_situation_sharing_web.controller;

import com.example.as_situation_sharing_web.service.UserService;
import com.example.as_situation_sharing_web.user.UserCreateForm;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login_form";
    }

    // 로그인 요청을 처리함
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/"; // 로그인 성공 시 메인 페이지로 리다이렉트
        } catch (Exception e) {
            return "redirect:/user/login?error=true"; // 로그인 실패 시 다시 로그인 페이지로 리다이렉트
        }
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("userCreateForm", new UserCreateForm());
        return "signup_form";
    }

    //Todo 로그인할때 빈 값으로 전송되는 문제 수정해야함.
    @PostMapping("/signup")
    @Operation(summary = "User signup", description = "새로운 사용자를 등록합니다.")
    public String signup(@Valid @ModelAttribute("userCreateForm") UserCreateForm userCreateForm,
                         BindingResult bindingResult,Model model) {

        System.out.println("UserCreateForm userid: " + userCreateForm.getUserid());
        System.out.println("UserCreateForm username: " + userCreateForm.getUsername());
        System.out.println("UserCreateForm password1: " + userCreateForm.getPassword1());
        System.out.println("UserCreateForm email: " + userCreateForm.getEmail());
        System.out.println("UserCreateForm role: " + userCreateForm.getRole());

        System.out.println("UserCreateForm: " + userCreateForm);
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.isPasswordConfirmed()) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.createUser(
                    userCreateForm.getUserid(),
                    userCreateForm.getPassword1(),
                    userCreateForm.getRole(),
                    userCreateForm.getUsername(),
                    userCreateForm.getEmail(),
                    userCreateForm.getPhoneNumber(),
                    userCreateForm.getBio());

        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            bindingResult.reject("signupFailed", "서버 에러가 발생했습니다.");
            return "signup_form";
        }

        return "redirect:/user/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }
}
