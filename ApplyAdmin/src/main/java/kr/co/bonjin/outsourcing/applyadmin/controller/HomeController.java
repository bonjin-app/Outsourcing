package kr.co.bonjin.outsourcing.applyadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * 기본 경로
     * @return
     */
    @GetMapping(value= "/home")
    public String rootPage() {
        return "home";
    }
}
