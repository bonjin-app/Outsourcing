package kr.co.bonjin.outsourcing.applyadmin.controller;

import kr.co.bonjin.outsourcing.applyadmin.controller.dto.HistoryResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.SmsHistory;
import kr.co.bonjin.outsourcing.applyadmin.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    /**
     * 목록 페이지
     * @param pageRequestDto
     * @param model
     * @return
     */
    @GetMapping(value = "/sms")
    public String listPage(PageRequestDto pageRequestDto, Model model) {
        PageResultDto<HistoryResponseDto, SmsHistory> list = historyService.findAllPageable(pageRequestDto);
        model.addAttribute("list", list);
        return "history/sms-list";
    }
}
