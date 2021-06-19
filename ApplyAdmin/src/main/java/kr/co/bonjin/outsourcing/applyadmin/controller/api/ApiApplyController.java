package kr.co.bonjin.outsourcing.applyadmin.controller.api;

import kr.co.bonjin.outsourcing.applyadmin.config.response.ApiDataResponse;
import kr.co.bonjin.outsourcing.applyadmin.config.response.ApiResponse;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.ApplyRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.Address;
import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import kr.co.bonjin.outsourcing.applyadmin.entity.Image;
import kr.co.bonjin.outsourcing.applyadmin.entity.SmsHistory;
import kr.co.bonjin.outsourcing.applyadmin.exception.ApiError;
import kr.co.bonjin.outsourcing.applyadmin.exception.ApiException;
import kr.co.bonjin.outsourcing.applyadmin.provider.SMSProvider;
import kr.co.bonjin.outsourcing.applyadmin.repository.DocumentRepository;
import kr.co.bonjin.outsourcing.applyadmin.repository.SmsHistoryRepository;
import kr.co.bonjin.outsourcing.applyadmin.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/apply")
public class ApiApplyController {
    private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;
    private final SmsHistoryRepository smsHistoryRepository;

    @PostMapping
    public ApiResponse register(ApplyRequestDto applyRequestDto, @RequestParam("file") MultipartFile file) {

        List<Document> result = documentRepository.findByPhone(applyRequestDto.getPhone());
        if (result.size() > 0) {
//            throw new ApiException(ApiError.MISSING_PARAMETER, "Phone Not Unique.");
            return new ApiDataResponse<>(ApiError.INVALID_PARAMETER);
        }

        try {
            String saveFileNm = UUID.randomUUID().toString();
            String fileName = file.getOriginalFilename();
            String savePath = fileStorageService.storeFile(file, saveFileNm);
            Image image = Image.builder()
                    .fileNm(fileName)
                    .fileExtn(fileName.substring(fileName.lastIndexOf(".")+1))
                    .fileData(file.getBytes())
                    .savePath(savePath)
                    .saveFileNm(saveFileNm)
                    .fileSize(file.getSize())
                    .build();

            Address address = Address.builder()
                    .postcode(applyRequestDto.getPostcode())
                    .address(applyRequestDto.getAddress())
                    .detailAddress(applyRequestDto.getAddressDetail())
                    .build();

            Document document = Document.builder()
                    .name(applyRequestDto.getName())
                    .residentId(applyRequestDto.getResidentId())
                    .phone(applyRequestDto.getPhone())
                    .recommender(applyRequestDto.getRecommender())
                    .address(address)
                    .image(image)
                    .build();

            documentRepository.save(document);

        } catch (Exception e) {
            throw new ApiException(ApiError.INTERNAL_SERVER_ERROR, "File Upload Failed.");
        }
        return new ApiDataResponse<>("");
    }

    @GetMapping(value = "/sms")
    public ApiResponse requestSms(String phone) {
        SMSProvider smsProvider = SMSProvider.getInstance();
        String code = smsProvider.excuteGenerate();
        String result = smsProvider.sendSMS(phone, code);

        SmsHistory smsHistory = SmsHistory.builder()
                .messageId("")
                .resultCode("")
                .message("")
                .receiver(phone)
                .authCode(code)
                .build();
        smsHistoryRepository.save(smsHistory);

        return new ApiDataResponse<>(result);
    }

    @PostMapping(value = "/sms/auth")
    public ApiResponse requestSmsAuth(String messageId, String authCode) {
        SmsHistory smsHistory = smsHistoryRepository.findByMessageId(messageId);
        if (ObjectUtils.isEmpty(smsHistory)) {
            return new ApiDataResponse<>(ApiError.INVALID_PARAMETER);
        }

        if (!smsHistory.getAuthCode().equals(authCode)) {
            return new ApiDataResponse<>(ApiError.MISSING_PARAMETER);
        }

        smsHistory.setIsAuth(true);
        return new ApiDataResponse<>("");
    }
}
