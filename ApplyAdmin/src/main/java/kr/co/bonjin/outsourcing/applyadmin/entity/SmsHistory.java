package kr.co.bonjin.outsourcing.applyadmin.entity;

import kr.co.bonjin.outsourcing.applyadmin.entity.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder // builder 를 사용할수 있게 합니다.
@Entity // jpa entity 임을 알립니다.
@Getter // 필드값의 getter 를 자동으로 생성합니다.
@Setter
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다. (JPA 필수)
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@ToString
@Table(name = "sms_history") // 'sms_history' 테이블과 매핑됨을 명시
public class SmsHistory extends BaseEntity {

    @Id
    @Column(name = "sms_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle 사용시 Sequence 로 바꾸기
    private Long id;

    // 메시지 고유 ID
    @Column(name = "message_id")
    private String messageId;

    // 결과코드
    @Column(name = "result_code")
    private String resultCode;

    // 보낸 메시지
    @Column(name = "send_message")
    private String sendMessage;

    // 결과 메시지
    @Column(name = "message")
    private String message;

    // 수신인
    @Column(name = "receiver")
    private String receiver;

    // 인증코드
    @Column(name = "auth_code")
    private String authCode;

    // 인증 완료 여부
    @Column(name = "is_auth")
    private boolean isAuth;
}
