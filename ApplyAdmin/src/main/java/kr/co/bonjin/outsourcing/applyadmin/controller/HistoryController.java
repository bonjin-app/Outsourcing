package kr.co.bonjin.outsourcing.applyadmin.controller;

import kr.co.bonjin.outsourcing.applyadmin.controller.dto.HistoryResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.SmsHistory;
import kr.co.bonjin.outsourcing.applyadmin.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    /**
     * Excel Download
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/excel/download")
    public void excelDownload(@ModelAttribute PageRequestDto pageRequestDto, HttpServletResponse response) throws IOException {
        List<HistoryResponseDto> smsHistory = historyService.findAll(pageRequestDto);

//        Workbook wb = new HSSFWorkbook();
        SXSSFWorkbook wb = new SXSSFWorkbook();
        SXSSFSheet sheet = wb.createSheet("Document Sheet");
        SXSSFRow row = null;
        SXSSFCell cell = null;
        int rowNum = 0;

        // Header
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("Number");
        cell = row.createCell(1);
        cell.setCellValue("ResultCode");
        cell = row.createCell(2);
        cell.setCellValue("Message");
        cell = row.createCell(3);
        cell.setCellValue("Receiver");
        cell = row.createCell(4);
        cell.setCellValue("MessageID");
        cell = row.createCell(5);
        cell.setCellValue("AuthCode");
        cell = row.createCell(6);
        cell.setCellValue("IsAush");
        cell = row.createCell(7);
        cell.setCellValue("CreateDate");

        // Body
        for (int i=0; i<smsHistory.size(); i++) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(smsHistory.get(i).getId());
            cell = row.createCell(1);
            cell.setCellValue(smsHistory.get(i).getResultCode());
            cell = row.createCell(2);
            cell.setCellValue(smsHistory.get(i).getMessage());
            cell = row.createCell(3);
            cell.setCellValue(smsHistory.get(i).getReceiver());
            cell = row.createCell(4);
            cell.setCellValue(smsHistory.get(i).getMessageId());
            cell = row.createCell(5);
            cell.setCellValue(smsHistory.get(i).getAuthCode());
            cell = row.createCell(6);
            cell.setCellValue(smsHistory.get(i).isAuth());
            cell = row.createCell(7);
            String createDate = smsHistory.get(i).getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            cell.setCellValue(createDate);
        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
//        response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename=history.xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }
}
