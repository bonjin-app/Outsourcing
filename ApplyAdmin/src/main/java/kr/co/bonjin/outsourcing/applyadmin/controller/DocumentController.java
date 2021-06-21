package kr.co.bonjin.outsourcing.applyadmin.controller;

import kr.co.bonjin.outsourcing.applyadmin.controller.dto.DocumentResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import kr.co.bonjin.outsourcing.applyadmin.entity.Image;
import kr.co.bonjin.outsourcing.applyadmin.repository.ImageRepository;
import kr.co.bonjin.outsourcing.applyadmin.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        PageResultDto<DocumentResponseDto, Document> list = documentService.findAllPageable(pageRequestDto);
        model.addAttribute("list", list);
        return "document/list";
    }

    /**
     * 상세 페이지
     * @param id
     * @param model
     * @return
     */
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
     * Document 삭제 Process
     * @param id
     * @return
     */
    @PostMapping(value = "/delete")
    public String processDocumentDelete(Long id) {
        documentService.deleteById(id);
        return "redirect:/document";
    }

    /**
     * Excel Download
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/excel/download")
    public void excelDownload(HttpServletResponse response) throws IOException {
        List<DocumentResponseDto> documents = documentService.findAll();

//        Workbook wb = new HSSFWorkbook();
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Document Sheet");
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        // Header
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("Number");
        cell = row.createCell(1);
        cell.setCellValue("Name");
        cell = row.createCell(2);
        cell.setCellValue("ResidentId");
        cell = row.createCell(3);
        cell.setCellValue("Phone");
        cell = row.createCell(4);
        cell.setCellValue("Gender");
        cell = row.createCell(5);
        cell.setCellValue("Address");
        cell = row.createCell(6);
        cell.setCellValue("AddressDetail");
        cell = row.createCell(7);
        cell.setCellValue("AddressPostcode");
        cell = row.createCell(8);
        cell.setCellValue("CreateDate");

        // Body
        for (int i=0; i<documents.size(); i++) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(documents.get(i).getId());
            cell = row.createCell(1);
            cell.setCellValue(documents.get(i).getName());
            cell = row.createCell(2);
            cell.setCellValue(documents.get(i).getResidentId());
            cell = row.createCell(3);
            cell.setCellValue(documents.get(i).getPhone());
            cell = row.createCell(4);
            cell.setCellValue(documents.get(i).getGender());
            cell = row.createCell(5);
            cell.setCellValue(documents.get(i).getAddress());
            cell = row.createCell(6);
            cell.setCellValue(documents.get(i).getAddressDetail());
            cell = row.createCell(7);
            cell.setCellValue(documents.get(i).getAddressPostcode());
            cell = row.createCell(8);
            String createDate = documents.get(i).getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            cell.setCellValue(createDate);
        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
//        response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename=document.xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }

    /**
     * 이미지 로드
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
