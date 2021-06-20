package kr.co.bonjin.outsourcing.applyadmin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ApplyController {

    @GetMapping(value = {"", "/", "apply"})
    public String registerPage() {
        return "apply/register";
    }

    @GetMapping(value = "/apply/complete")
    public String completePage() {
        return "apply/complete";
    }
}
