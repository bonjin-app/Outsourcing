package kr.co.bonjin.outsourcing.applyadmin.common;

import lombok.RequiredArgsConstructor;

import java.util.Base64;
import java.util.UUID;

@RequiredArgsConstructor
public class ServiceUtil {

    /**
     *
     * @param str
     * @return
     */
    public static String base64Encoded(String str) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodeByte = encoder.encode(str.getBytes());
        return new String(encodeByte);
    }

    /**
     *
     * @param str
     * @return
     */
    public static String base64Decoded(String str) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodeByte = decoder.decode(str);
        String fileName = new String(decodeByte);
        return fileName;
    }

    /**
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
