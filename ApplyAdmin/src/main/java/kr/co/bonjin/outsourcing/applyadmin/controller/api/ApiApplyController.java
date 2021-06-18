package kr.co.bonjin.outsourcing.applyadmin.controller.api;

import kr.co.bonjin.outsourcing.applyadmin.config.response.ApiDataResponse;
import kr.co.bonjin.outsourcing.applyadmin.config.response.ApiResponse;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.ApplyRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/apply")
public class ApiApplyController {

    @PostMapping
    public ApiResponse register(ApplyRequestDto applyRequestDto, @RequestParam("file") MultipartFile file) {
        System.out.println("register");

        System.out.println("applyRequestDto = " + applyRequestDto);
        System.out.println("file = " + file);

        return new ApiDataResponse<>("");
    }
}
