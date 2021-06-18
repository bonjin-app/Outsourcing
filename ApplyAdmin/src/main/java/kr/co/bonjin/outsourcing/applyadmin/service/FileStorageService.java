package kr.co.bonjin.outsourcing.applyadmin.service;

import kr.co.bonjin.outsourcing.applyadmin.common.ServiceUtil;
import kr.co.bonjin.outsourcing.applyadmin.exception.ApiError;
import kr.co.bonjin.outsourcing.applyadmin.exception.ApiException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileStorageService {

    /**
     * @Value 생성자에서 사용금지. null
     * 파일 저장 경로
     */
    @Value("${upload-path.directory}")
    private String uploadPath;

    private Path fileStorageLocation;

    public FileStorageService() {

    }

    /**
     * 객체가 생성된 후에 초기화. 환경변수 사용가능.
     */
    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadPath).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);

        } catch (Exception ex) {
            throw new ApiException(ApiError.INVALID_PATH, "Could not Create the directory where the uploaded files will be stored" + ex);
        }
    }

    /**
     * File Upload
     * @param file
     * @return
     */
    public String storeFile(MultipartFile file, String saveFileNm) {

        // 앱에서 Base64 Encode 해서 보낸 데이터 Decode
        String decodeFileName = ServiceUtil.base64Decoded(file.getOriginalFilename());

        // 파일 이름 표준화
        String fileName = StringUtils.cleanPath(decodeFileName);
        String uploadPath = getUploadPath();

        try {
            // 파일의 이름에 유효하지 않은 문자가 포함되어 있는지 확인
            if (fileName.contains("..")) {
                throw new ApiException(ApiError.INVALID_PATH, fileName);
            }

            // 대상 위치로 파일 복사 (기존 파일을 같은 이름으로 바꾸기)
            Path targetLocation = this.fileStorageLocation.resolve(fileStorageLocation+uploadPath+saveFileNm);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return uploadPath;

        } catch (IOException ex) {
            throw new ApiException(ApiError.FILE_NOT_FOUND, fileName + ". Please try again!" + ex);
        }
    }

    /**
     * File Download Service
     * @param request
     * @param saveFilePath
     * @param fileName
     * @return
     */
    public ResponseEntity<Resource> fileDownload(HttpServletRequest request, String saveFilePath, String fileName) {

        // 리소스로 파일로드
        Resource resource = loadFileAsResource(saveFilePath);

        return fileEntity(request, resource, fileName);
    }

    /**
     * File Download
     * @param request
     * @param resource
     * @param fileName
     * @return
     */
    private ResponseEntity<Resource> fileEntity(HttpServletRequest request, Resource resource, String fileName) {
        // 파일의 콘텐츠 유형 확인
        String contentType = null;
        String encFileName = "";

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            // 파일명 URLEncoding -> '공백' -> '+' 로 변경되어 다시 스페이스로 변환
            encFileName = URLEncoder.encode(fileName, "utf-8").replaceAll( "\\+", "%20" );
        } catch (IOException e) {
            throw new ApiException(ApiError.FILE_NOT_FOUND, fileName + e);
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encFileName + "\"")
                .body(resource);
    }

    /**
     * File Resource
     * @param fileName
     * @return
     */
    private Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new ApiException(ApiError.FILE_NOT_FOUND, fileName);
            }

        } catch (MalformedURLException e) {
            throw new ApiException(ApiError.FILE_NOT_FOUND, fileName + e);
        }
    }

    /**
     * upload path
     * @return
     */
    private String getUploadPath() {
        String dateFormat = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());

        File fileDir = new File(fileStorageLocation+dateFormat);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return dateFormat;
    }

}