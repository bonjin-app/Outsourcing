package kr.co.bonjin.outsourcing.applyadmin.controller.dto;

import kr.co.bonjin.outsourcing.applyadmin.entity.SmsHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HistoryResponseDto {

    private Long id;
    private String resultCode;
    private String sendMessage;
    private String message;
    private String receiver;
    private String messageId;
    private String authCode;
    private boolean isAuth;
    private LocalDateTime createdDate;

    public HistoryResponseDto(SmsHistory history) {
        this.id = history.getId();
        this.resultCode = history.getResultCode();
        this.sendMessage = history.getSendMessage();
        this.message = history.getMessage();
        this.receiver = history.getReceiver();
        this.messageId = history.getMessageId();
        this.authCode = history.getAuthCode();
        this.isAuth = history.isAuth();
        this.createdDate = history.getCreatedDate();
    }
}
