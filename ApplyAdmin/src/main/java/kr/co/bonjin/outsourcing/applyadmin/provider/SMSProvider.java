package kr.co.bonjin.outsourcing.applyadmin.provider;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Random;

public class SMSProvider {

    //Instance
    private static SMSProvider instance;

    private String USER_ID = "kcca1160";
    private String KEY = "jh99hwyjdhwjn76ik6ch0hf5p2y3h9p2";
    private String SENDER = "01037152256";
    private int certNumLength = 6;

    //private construct
    private SMSProvider() {}

    public static synchronized SMSProvider getInstance() {
        if (instance == null) { instance = new SMSProvider();}
        return instance;
    }

    public String sendSMS(String receiver, String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://apis.aligo.in/send/"; // 전송요청 URL

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        /******************** 인증정보 ********************/
        parameters.add("user_id", USER_ID); // SMS 아이디
        parameters.add("key", KEY); //인증키
        /******************** 인증정보 ********************/

        /******************** 전송정보 ********************/
        parameters.add("msg", "["+code+"] 인증 코드 입니다."); // 메세지 내용
        parameters.add("receiver", receiver); // 수신번호
        parameters.add("sender", SENDER); // 발신번호
        parameters.add("testmode_yn", "Y"); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
        parameters.add("title", "[인증번호]"); //  LMS, MMS 제목 (미입력시 본문중 44Byte 또는 엔터 구분자 첫라인)
        /******************** 전송정보 ********************/
        ResponseEntity<String> entity = restTemplate.postForEntity(url, parameters, String.class);

        return entity.getBody();
    }

    public String excuteGenerate() {
        Random random = new Random(System.currentTimeMillis());

        int range = (int)Math.pow(10,certNumLength);
        int trim = (int)Math.pow(10, certNumLength-1);
        int result = random.nextInt(range)+trim;

        if(result>range){
            result = result - trim;
        }
        return String.valueOf(result);
    }

    public int getCertNumLength() {
        return certNumLength;
    }

    public void setCertNumLength(int certNumLength) {
        this.certNumLength = certNumLength;
    }
}
