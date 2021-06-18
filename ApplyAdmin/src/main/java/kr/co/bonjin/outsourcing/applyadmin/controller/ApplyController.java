package kr.co.bonjin.outsourcing.applyadmin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/apply")
@RequiredArgsConstructor
public class ApplyController {

    @GetMapping
    public String registerPage() {
        return "apply/register";
    }

    @GetMapping(value = "/complete")
    public String completePage() {
        return "apply/complete";
    }
}
