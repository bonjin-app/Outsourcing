package kr.co.bonjin.outsourcing.applyadmin.controller;

import kr.co.bonjin.outsourcing.applyadmin.controller.dto.DocumentResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import kr.co.bonjin.outsourcing.applyadmin.entity.Image;
import kr.co.bonjin.outsourcing.applyadmin.repository.ImageRepository;
import kr.co.bonjin.outsourcing.applyadmin.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final ImageRepository imageRepository;

    /**
     * 목록 페이지
     * @param pageRequestDto
     * @param model
     * @return
     */
    @GetMapping
    public String listPage(PageRequestDto pageRequestDto, Model model) {
        PageResultDto<DocumentResponseDto, Document> list = documentService.findAllPageableIdolGroups(pageRequestDto);
        model.addAttribute("list", list);
        return "document/list";
    }

    @GetMapping(value = "/{id}")
    public String detailPage(@PathVariable Long id, Model model) {
        DocumentResponseDto document = documentService.findById(id);

        // 값이 없으면 목록으로 Redirect
        if (ObjectUtils.isEmpty(document)) {
            return "redirect:/document";
        }

        model.addAttribute("document", document);
        return "document/detail";
    }

    /**
     * IDOL_GROUP 이미지 로드
     * @return
     */
    @GetMapping(value = "/attachments/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long fileId) {

        Image image = imageRepository.findById(fileId).get();

        if (!ObjectUtils.isEmpty(image) && image.getFileData().length > 0) {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(image.getFileData(), headers, HttpStatus.OK);
        }
        return null;
    }
}
