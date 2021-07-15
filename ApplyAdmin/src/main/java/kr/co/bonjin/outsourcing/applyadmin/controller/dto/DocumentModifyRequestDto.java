package kr.co.bonjin.outsourcing.applyadmin.controller.dto;

import lombok.Data;

@Data
public class DocumentModifyRequestDto {
    private Long id;
    private String name;
    private String address;
    private String addressDetail;
    private String addressPostcode;
}
