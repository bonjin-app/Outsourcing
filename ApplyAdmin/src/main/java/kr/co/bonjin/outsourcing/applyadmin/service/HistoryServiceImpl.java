package kr.co.bonjin.outsourcing.applyadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.DocumentResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.HistoryResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import kr.co.bonjin.outsourcing.applyadmin.entity.SmsHistory;
import kr.co.bonjin.outsourcing.applyadmin.provider.SMSProvider;
import kr.co.bonjin.outsourcing.applyadmin.repository.SmsHistoryRepository;
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
import java.util.Map;
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
    public List<HistoryResponseDto> findAll(PageRequestDto pageRequestDto) {
        List<SmsHistory> result;
        if (!ObjectUtils.isEmpty(pageRequestDto.getKeyword())) {

            if (!ObjectUtils.isEmpty(pageRequestDto.getStartdate()) && !ObjectUtils.isEmpty(pageRequestDto.getEnddate())) {
                String startdate = pageRequestDto.getStartdate();
                String enddate = pageRequestDto.getEnddate();
                LocalDateTime before = LocalDate.parse(startdate).atTime(0,0);
                LocalDateTime after = LocalDate.parse(enddate).atTime(23,0);
                result = historyRepository.findByReceiverContainsAndCreatedDateBetween(pageRequestDto.getKeyword(),
                        before,
                        after);

            } else {
                result = historyRepository.findByReceiverContains(pageRequestDto.getKeyword());
            }

        } else if (!ObjectUtils.isEmpty(pageRequestDto.getStartdate()) && !ObjectUtils.isEmpty(pageRequestDto.getEnddate())) {
            String startdate = pageRequestDto.getStartdate();
            String enddate = pageRequestDto.getEnddate();
            LocalDateTime before = LocalDate.parse(startdate).atTime(0,0);
            LocalDateTime after = LocalDate.parse(enddate).atTime(23,0);
            result = historyRepository.findByCreatedDateBetween(before, after);

        } else {
            result = historyRepository.findAll();
        }

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

            if (!ObjectUtils.isEmpty(pageRequestDto.getStartdate()) && !ObjectUtils.isEmpty(pageRequestDto.getEnddate())) {
                String startdate = pageRequestDto.getStartdate();
                String enddate = pageRequestDto.getEnddate();
                LocalDateTime before = LocalDate.parse(startdate).atTime(0,0);
                LocalDateTime after = LocalDate.parse(enddate).atTime(23,0);
                result = historyRepository.findByReceiverContainsAndCreatedDateBetween(pageRequestDto.getKeyword(),
                                                                                        before,
                                                                                        after,
                                                                                        pageable);

            } else {
                result = historyRepository.findByReceiverContains(pageRequestDto.getKeyword(),
                        pageable);
            }

        } else if (!ObjectUtils.isEmpty(pageRequestDto.getStartdate()) && !ObjectUtils.isEmpty(pageRequestDto.getEnddate())) {
            String startdate = pageRequestDto.getStartdate();
            String enddate = pageRequestDto.getEnddate();
            LocalDateTime before = LocalDate.parse(startdate).atTime(0,0);
            LocalDateTime after = LocalDate.parse(enddate).atTime(23,0);
            result = historyRepository.findByCreatedDateBetween(before, after, pageable);

        } else {
            result = historyRepository.findAll(pageable);
        }

        Function<SmsHistory, HistoryResponseDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }

    @Override
    public String sendSMSCode(String receiver) throws JsonProcessingException {
        SMSProvider smsProvider = SMSProvider.getInstance();
        String code = smsProvider.excuteGenerate();
        String sendMessage = "["+code+"] 인증번호 입니다.";
        String json = smsProvider.sendSMS(receiver, sendMessage);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        String resultCode = String.valueOf(map.get("result_code"));
        String message = String.valueOf(map.get("message"));
        String msgId = String.valueOf(map.get("msg_id"));

        SmsHistory smsHistory = SmsHistory.builder()
                .messageId(msgId)
                .resultCode(resultCode)
                .sendMessage(sendMessage)
                .message(message)
                .receiver(receiver)
                .authCode(code)
                .build();
        historyRepository.save(smsHistory);

        return json;
    }


    @Override
    public String sendSMSSuccess(String receiver, String name) throws JsonProcessingException {
        SMSProvider smsProvider = SMSProvider.getInstance();
        String sendMessage = name + "님 신청서 접수가 완료되었습니다.";
        String json = smsProvider.sendSMS(receiver, sendMessage);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        String resultCode = String.valueOf(map.get("result_code"));
        String message = String.valueOf(map.get("message"));
        String msgId = String.valueOf(map.get("msg_id"));

        SmsHistory smsHistory = SmsHistory.builder()
                .messageId(msgId)
                .resultCode(resultCode)
                .sendMessage(sendMessage)
                .message(message)
                .receiver(receiver)
                .build();
        historyRepository.save(smsHistory);

        return json;
    }
}
