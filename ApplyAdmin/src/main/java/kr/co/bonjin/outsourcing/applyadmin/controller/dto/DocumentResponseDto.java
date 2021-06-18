package kr.co.bonjin.outsourcing.applyadmin.controller.dto;

import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentResponseDto {

    private Long id;
    private String name;
    private String residentId;
    private String phone;
    private String address;
    private String recommender;
    private LocalDateTime createdDate;

    public DocumentResponseDto(Document document) {
        this.id = document.getId();
        this.name = document.getName();
        this.residentId = document.getResidentId();
        this.phone = document.getPhone();
        this.address = document.getAddress().getAddress();
        this.recommender = document.getRecommender();
        this.createdDate = document.getCreatedDate();
    }
}
