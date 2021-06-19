package kr.co.bonjin.outsourcing.applyadmin.service;

import kr.co.bonjin.outsourcing.applyadmin.controller.dto.DocumentResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import kr.co.bonjin.outsourcing.applyadmin.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public PageResultDto<DocumentResponseDto, Document> findAllPageableIdolGroups(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable(Sort.by("id").descending());

        Page<Document> result;
        if (!ObjectUtils.isEmpty(pageRequestDto.getKeyword())) {
            result = documentRepository.findByNameContainsOrPhoneContains(pageRequestDto.getKeyword(),
                    pageRequestDto.getKeyword(),
                    pageable);
        } else {
            result = documentRepository.findAll(pageable);
        }

        Function<Document, DocumentResponseDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }

    @Override
    public DocumentResponseDto findById(Long id) {
        Document result = documentRepository.findById(id).get();
        DocumentResponseDto dto = entityToDto(result);
        return dto;
    }
}
