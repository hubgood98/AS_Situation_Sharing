package com.example.as_situation_sharing_web.user;

import com.example.as_situation_sharing_web.domain.UserData;
import com.example.as_situation_sharing_web.dto.user.UserRequest;
import com.example.as_situation_sharing_web.dto.user.UserResponse;
import com.example.as_situation_sharing_web.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User signup, validation, and login management")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

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

    @GetMapping("/checkUserid")
    @Operation(summary = "Check if UserID exists", description = "특정 UserID가 이미 사용 중인지 확인합니다.")
    public ResponseEntity<Map<String, Boolean>> checkUserid(
            @Parameter(description = "UserID to check", example = "john_doe") @RequestParam String userid) {

        boolean exists = userService.isUserIdTaken(userid);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/checkEmail")
    @Operation(summary = "Check if Email exists", description = "Checks whether a given Email is already taken")
    public ResponseEntity<Map<String, Boolean>> checkEmail(
            @Parameter(description = "Email to check", example = "john@example.com") @RequestParam String email) {

        boolean exists = userService.isEmailTaken(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userid}")
    @Operation(summary = "Get user details", description = "Retrieves details of a user by UserID")
    public ResponseEntity<UserResponse> getUserByUserId(
            @Parameter(description = "UserID of the user", example = "john_doe") @PathVariable String userid) {

        UserData userData = userService.getUserByUserId(userid);
        UserResponse userResponse = UserResponse.builder()
                .id(userData.getId())
                .userid(userData.getUserid())
                .username(userData.getUsername())
                .email(userData.getEmail())
                .phoneNumber(userData.getPhone_number())
                .bio(userData.getBio())
                .role(userData.getRole())
                .build();

        return ResponseEntity.ok(userResponse);
    }
}
