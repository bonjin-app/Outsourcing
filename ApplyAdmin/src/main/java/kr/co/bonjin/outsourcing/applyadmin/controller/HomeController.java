package kr.co.bonjin.outsourcing.applyadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * 기본 경로
     * @return
     */
    @GetMapping(value= "/")
    public String root() {
        return "home";
    }

    @GetMapping(value = {"/home", "/main"})
    public String home() {
        return "redirect:/";
    }
}
