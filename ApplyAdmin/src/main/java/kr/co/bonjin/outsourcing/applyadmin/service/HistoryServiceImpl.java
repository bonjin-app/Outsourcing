package kr.co.bonjin.outsourcing.applyadmin.service;

import kr.co.bonjin.outsourcing.applyadmin.controller.dto.DocumentResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.HistoryResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import kr.co.bonjin.outsourcing.applyadmin.entity.SmsHistory;
import kr.co.bonjin.outsourcing.applyadmin.repository.SmsHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HistoryServiceImpl implements HistoryService {

    private final SmsHistoryRepository historyRepository;

    @Override
    public List<HistoryResponseDto> findAll() {
        List<SmsHistory> result = historyRepository.findAll();
        List<HistoryResponseDto> list = result.stream()
                .map(m -> {
                    HistoryResponseDto dto = entityToDto(m);
                    return dto;
                }).collect(Collectors.toList());
        return list;
    }

    @Override
    public PageResultDto<HistoryResponseDto, SmsHistory> findAllPageable(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable(Sort.by("id").descending());

        Page<SmsHistory> result;
        if (!ObjectUtils.isEmpty(pageRequestDto.getKeyword())) {
            result = historyRepository.findByReceiverContains(pageRequestDto.getKeyword(),
                                                        pageable);
        } else {
            result = historyRepository.findAll(pageable);
        }

        Function<SmsHistory, HistoryResponseDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }
}
