package kr.co.bonjin.outsourcing.applyadmin.provider;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SMSProviderTest {

    @Test
    void SMS_TEST() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));    //Response Header to UTF-8

//        String url = "https://apis.aligo.in/send/"; // 전송요청 URL
        String url = "https://apis.aligo.in/send/"; // 전송요청 URL

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        /******************** 인증정보 ********************/
        parameters.add("user_id", "kcca1160"); // SMS 아이디
        parameters.add("key", "jh99hwyjdhwjn76ik6ch0hf5p2y3h9p2"); //인증키
        /******************** 인증정보 ********************/

        /******************** 전송정보 ********************/
        parameters.add("msg", "%고객명%님. 안녕하세요. API TEST SEND"); // 메세지 내용
        parameters.add("receiver", "01050545654"); // 수신번호
        parameters.add("destination", "01111111112|홍길동"); // 수신인 %고객명% 치환
        parameters.add("sender", "01050545654"); // 발신번호
        parameters.add("testmode_yn", "Y"); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
        parameters.add("title", "인증번호"); //  LMS, MMS 제목 (미입력시 본문중 44Byte 또는 엔터 구분자 첫라인)
        /******************** 전송정보 ********************/
        ResponseEntity<String> entity = restTemplate.postForEntity(url, parameters, String.class);
        System.out.println("entity = " + entity.getBody());
    }

}