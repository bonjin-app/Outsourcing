package kr.co.bonjin.outsourcing.applyadmin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    /**
     * 로그인 페이지
     * @return
     */
    @GetMapping("/member/login")
    public String goLoginPage() {
        return "member/login";
    }

    /**
     * 회원가입 페이지
     * @return
     */
    @GetMapping("/member/register")
    public String goRegisterPage() {
        return "member/register";
    }

    /**
     * 비밀번호 찾기 페이지
     * @return
     */
    @GetMapping("/member/forgot-password")
    public String goForgotPasswordPage() {
        return "member/forgot-password";
    }
}
