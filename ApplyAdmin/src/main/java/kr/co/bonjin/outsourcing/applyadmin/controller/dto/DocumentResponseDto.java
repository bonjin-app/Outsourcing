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

    private String name;
    private String residentId;
    private String recommender;
    private LocalDateTime createdDate;

    public DocumentResponseDto(Document document) {
        this.name = document.getName();
        this.residentId = document.getResidentId();
        this.recommender = document.getRecommender();
        this.createdDate = document.getCreatedDate();
    }
}
