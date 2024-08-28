package com.example.as_situation_sharing_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/static/**").permitAll() // 정적 리소스 경로 허용
                                .requestMatchers(new AntPathRequestMatcher("/user/login"),
                                        new AntPathRequestMatcher("/user/signup"),
                                        new AntPathRequestMatcher("/h2-console/**"),
                                        new AntPathRequestMatcher("/home")).permitAll() // 로그인 및 회원가입 경로 허용
                                .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                )
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")) // CSRF 비활성화 설정
                )
                .headers(headers ->
                        headers.addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/user/login") // 사용자 정의 로그인 페이지
                                .failureUrl("/user/login?error=true") // 로그인 실패 시 리다이렉트
                                .defaultSuccessUrl("/api/repair/list", true) // 로그인 성공 시 리다이렉트
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                );

        return http.build();
    }



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}