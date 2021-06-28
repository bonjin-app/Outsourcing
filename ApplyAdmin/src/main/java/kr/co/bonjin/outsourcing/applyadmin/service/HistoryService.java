package kr.co.bonjin.outsourcing.applyadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.HistoryResponseDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageRequestDto;
import kr.co.bonjin.outsourcing.applyadmin.controller.dto.PageResultDto;
import kr.co.bonjin.outsourcing.applyadmin.entity.SmsHistory;

import java.util.List;

public interface HistoryService {

    /**
     * 전체 조회
     * @return
     */
    List<HistoryResponseDto> findAll();

    /**
     * 전체 조회
     * @return
     */
    List<HistoryResponseDto> findAll(PageRequestDto pageRequestDto);

    String sendSMSCode(String receiver) throws JsonProcessingException;

    String sendSMSSuccess(String receiver, String name) throws JsonProcessingException;

    /**
     * 전체 조회 - 페이징
     * @param pageRequestDto
     * @return
     */
    PageResultDto<HistoryResponseDto, SmsHistory> findAllPageable(PageRequestDto pageRequestDto);

    default HistoryResponseDto entityToDto(SmsHistory entity) {
        HistoryResponseDto dto = HistoryResponseDto.builder()
                .id(entity.getId())
                .resultCode(entity.getResultCode())
                .sendMessage(entity.getSendMessage())
                .message(entity.getMessage())
                .receiver(entity.getReceiver())
                .messageId(entity.getMessageId())
                .authCode(entity.getAuthCode())
                .isAuth(entity.isAuth())
                .createdDate(entity.getCreatedDate())
                .build();
        return dto;
    }
}
