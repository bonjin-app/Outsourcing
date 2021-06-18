package kr.co.bonjin.outsourcing.applyadmin.controller;

import kr.co.bonjin.outsourcing.applyadmin.controller.dto.DocumentResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import kr.co.bonjin.outsourcing.applyadmin.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping
    public String listPage(PageRequestDto pageRequestDto, Model model) {
        PageResultDto<DocumentResponseDto, Document> list = documentService.findAllPageableIdolGroups(pageRequestDto);
        model.addAttribute("list", list);
        return "document/document-list";
    }
}
