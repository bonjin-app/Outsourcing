package kr.co.bonjin.outsourcing.applyadmin.service;

import kr.co.bonjin.outsourcing.applyadmin.controller.dto.HistoryResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.SmsHistory;
import kr.co.bonjin.outsourcing.applyadmin.repository.SmsHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HistoryServiceImpl implements HistoryService {

    private final SmsHistoryRepository historyRepository;

    @Override
    public PageResultDto<HistoryResponseDto, SmsHistory> findAllPageable(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable(Sort.by("id").descending());

        Page<SmsHistory> result = historyRepository.findAll(pageable);

        Function<SmsHistory, HistoryResponseDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }
}
