package kr.co.bonjin.outsourcing.applyadmin.controller.api;

import kr.co.bonjin.outsourcing.applyadmin.config.response.ApiDataResponse;
import kr.co.bonjin.outsourcing.applyadmin.config.response.ApiResponse;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.ApplyRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.Address;
import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import kr.co.bonjin.outsourcing.applyadmin.entity.Image;
import kr.co.bonjin.outsourcing.applyadmin.exception.ApiError;
import kr.co.bonjin.outsourcing.applyadmin.exception.ApiException;
import kr.co.bonjin.outsourcing.applyadmin.repository.DocumentRepository;
import kr.co.bonjin.outsourcing.applyadmin.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/apply")
public class ApiApplyController {

    private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;

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
}
