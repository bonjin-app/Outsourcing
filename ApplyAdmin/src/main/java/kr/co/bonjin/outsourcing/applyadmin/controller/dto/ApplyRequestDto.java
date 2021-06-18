package kr.co.bonjin.outsourcing.applyadmin.controller.dto;

import lombok.Data;

@Data
public class ApplyRequestDto {

    private String name;
    private String residentId;
    private String phone;
    private String address;
    private String addressDetail;
    private String postcode;
    private String recommender;
    private String dataUrl;
}
