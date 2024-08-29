package com.example.as_situation_sharing_web.controller;

import com.example.as_situation_sharing_web.service.UserService;
import com.example.as_situation_sharing_web.user.UserCreateForm;
import com.example.as_situation_sharing_web.user.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String signup(
            @RequestParam("userid") String userid,
            @RequestParam("username") String username,
            @RequestParam("password1") String password1,
            @RequestParam("password2") String password2,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("role") String role,
            Model model) {

        // 수동 유효성 검사
        if (userid == null || userid.trim().isEmpty()) {
            model.addAttribute("useridError", "사용자 ID는 필수 항목입니다.");
        }
        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("usernameError", "사용자 이름은 필수 항목입니다.");
        }
        if (password1 == null || password1.length() < 8) {
            model.addAttribute("password1Error", "비밀번호는 최소 8자 이상이어야 합니다.");
        }
        if (!password1.equals(password2)) {
            model.addAttribute("password2Error", "2개의 비밀번호가 일치하지 않습니다.");
        }
        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("emailError", "이메일은 필수 항목입니다.");
        }
        if (role == null || (!role.equals("CUSTOMER") && !role.equals("TECHNICIAN"))) {
            model.addAttribute("roleError", "올바른 사용자 역할을 선택하세요.");
        }

        // 오류가 있을 경우, 다시 폼으로 이동
        if (model.containsAttribute("useridError") || model.containsAttribute("usernameError") ||
                model.containsAttribute("password1Error") || model.containsAttribute("password2Error") ||
                model.containsAttribute("emailError") || model.containsAttribute("roleError")) {
            return "signup_form";
        }

        try {
            // userService를 통해 사용자 생성
            userService.createUser(userid, password1, UserRole.valueOf(role), username, email, phoneNumber, "");
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            model.addAttribute("signupFailed", "서버 에러가 발생했습니다.");
            return "signup_form";
        }

        return "redirect:/user/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }

}
