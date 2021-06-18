package kr.co.bonjin.outsourcing.applyadmin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberController {

    /**
     * 로그인 페이지
     * @return
     */
    @GetMapping("/login")
    public String goLoginPage() {
        return "sample/dashboard";
    }

    /**
     * 회원가입 페이지
     * @return
     */
    @GetMapping("/register")
    public String goRegisterPage() {
        return "member/register";
    }

    /**
     * 비밀번호 찾기 페이지
     * @return
     */
    @GetMapping("/forgot-password")
    public String goForgotPasswordPage() {
        return "member/forgot-password";
    }
}
