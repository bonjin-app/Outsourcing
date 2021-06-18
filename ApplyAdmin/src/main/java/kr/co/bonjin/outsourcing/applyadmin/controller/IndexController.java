package kr.co.bonjin.outsourcing.applyadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * 기본 경로
     * @return
     */
    @GetMapping(value= "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = {"/index", "/main"})
    public String main() {
        return "redirect:/";
    }
}
