package kr.co.bonjin.outsourcing.applyadmin.service;

import kr.co.bonjin.outsourcing.applyadmin.controller.dto.DocumentResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.Document;

public interface DocumentService {
    /**
     * 전체 조회 - 페이징
     * @param pageRequestDto
     * @return
     */
    PageResultDto<DocumentResponseDto, Document> findAllPageableIdolGroups(PageRequestDto pageRequestDto);

    default DocumentResponseDto entityToDto(Document entity) {
        DocumentResponseDto dto = DocumentResponseDto.builder()
                .name(entity.getName())
                .residentId(entity.getResidentId())
                .createdDate(entity.getCreatedDate())
                .build();
        return dto;
    }
}
