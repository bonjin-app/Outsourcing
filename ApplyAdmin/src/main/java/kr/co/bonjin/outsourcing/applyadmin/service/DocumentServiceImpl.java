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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public List<DocumentResponseDto> findAll() {
        List<Document> result = documentRepository.findAll();
        List<DocumentResponseDto> list = result.stream()
                .map(m -> {
                    DocumentResponseDto dto = entityToDto(m);
                    return dto;
                }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<DocumentResponseDto> findAll(PageRequestDto pageRequestDto) {
        List<Document> result;
        if (!ObjectUtils.isEmpty(pageRequestDto.getKeyword())) {

            if (!ObjectUtils.isEmpty(pageRequestDto.getStartdate())) {
                String date = pageRequestDto.getStartdate();
                LocalDateTime before = LocalDate.parse(date).atTime(0,0);
                LocalDateTime after = LocalDate.parse(date).atTime(23,0);
                result = documentRepository.findByNameContainsOrPhoneContainsAndCreatedDateBetween(pageRequestDto.getKeyword(),
                        pageRequestDto.getKeyword(),
                        before,
                        after);

            } else {
                result = documentRepository.findByNameContainsOrPhoneContains(pageRequestDto.getKeyword(),
                        pageRequestDto.getKeyword());
            }

        } else if (!ObjectUtils.isEmpty(pageRequestDto.getStartdate())) {
            String date = pageRequestDto.getStartdate();
            LocalDateTime before = LocalDate.parse(date).atTime(0,0);
            LocalDateTime after = LocalDate.parse(date).atTime(23,0);
            result = documentRepository.findByCreatedDateBetween(before, after);

        } else {
            result = documentRepository.findAll();
        }

        List<DocumentResponseDto> list = result.stream()
                .map(m -> {
                    DocumentResponseDto dto = entityToDto(m);
                    return dto;
                }).collect(Collectors.toList());
        return list;
    }

    @Override
    public PageResultDto<DocumentResponseDto, Document> findAllPageable(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable(Sort.by("id").descending());

        Page<Document> result;
        if (!ObjectUtils.isEmpty(pageRequestDto.getKeyword())) {

            if (!ObjectUtils.isEmpty(pageRequestDto.getStartdate())) {
                String date = pageRequestDto.getStartdate();
                LocalDateTime before = LocalDate.parse(date).atTime(0,0);
                LocalDateTime after = LocalDate.parse(date).atTime(23,0);
                result = documentRepository.findByNameContainsOrPhoneContainsAndCreatedDateBetween(pageRequestDto.getKeyword(),
                                                                                                pageRequestDto.getKeyword(),
                                                                                                before,
                                                                                                after,
                                                                                                pageable);

            } else {
                result = documentRepository.findByNameContainsOrPhoneContains(pageRequestDto.getKeyword(),
                        pageRequestDto.getKeyword(),
                        pageable);
            }

        } else if (!ObjectUtils.isEmpty(pageRequestDto.getStartdate())) {
            String date = pageRequestDto.getStartdate();
            LocalDateTime before = LocalDate.parse(date).atTime(0,0);
            LocalDateTime after = LocalDate.parse(date).atTime(23,0);
            result = documentRepository.findByCreatedDateBetween(before, after, pageable);

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

    @Override
    @Transactional
    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }
}
