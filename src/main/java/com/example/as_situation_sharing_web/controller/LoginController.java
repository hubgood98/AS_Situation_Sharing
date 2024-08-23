package com.example.as_situation_sharing_web.controller;

import com.example.as_situation_sharing_web.dto.user.UserRequest;
import com.example.as_situation_sharing_web.service.UserService;
import com.example.as_situation_sharing_web.user.UserCreateForm;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/signup")
    @Operation(summary = "User signup", description = "새로운 사용자를 등록합니다.")
    public ResponseEntity<?> signup(
            @Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            userService.createUser(userRequest.getUserid(), userRequest.getPassword(),
                    userRequest.getRole(), userRequest.getUsername(),
                    userRequest.getEmail(), userRequest.getPhoneNumber(),
                    userRequest.getBio());

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 등록된 사용자입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러가 발생했습니다.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
