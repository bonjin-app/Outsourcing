package kr.co.bonjin.outsourcing.applyadmin.service;

import kr.co.bonjin.outsourcing.applyadmin.controller.dto.DocumentResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.Document;

import java.util.List;

public interface DocumentService {

    /**
     * 전체 조회
     * @return
     */
    List<DocumentResponseDto> findAll();

    /**
     * 전체 조회 - 페이징
     * @param pageRequestDto
     * @return
     */
    PageResultDto<DocumentResponseDto, Document> findAllPageable(PageRequestDto pageRequestDto);

    /**
     * 단건 조회
     * @param id
     * @return
     */
    DocumentResponseDto findById(Long id);

    /**
     * 한건 삭제
     * @param id
     * @return
     */
    void deleteById(Long id);

    default DocumentResponseDto entityToDto(Document entity) {
        DocumentResponseDto dto = DocumentResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .residentId(entity.getResidentId())
                .recommender(entity.getRecommender())
                .gender(entity.getGender())
                .phone(entity.getPhone())
                .address(entity.getAddress().getAddress())
                .addressDetail(entity.getAddress().getDetailAddress())
                .addressPostcode(entity.getAddress().getPostcode())
                .fileId(entity.getImage().getId())
                .createdDate(entity.getCreatedDate())
                .build();
        return dto;
    }
}
