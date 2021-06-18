package kr.co.bonjin.outsourcing.applyadmin.entity;

import lombok.*;

import javax.persistence.*;

@Builder // builder 를 사용할수 있게 합니다.
@Entity // jpa entity 임을 알립니다.
@Getter // 필드값의 getter 를 자동으로 생성합니다.
@Setter
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다. (JPA 필수)
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@ToString
@Table(name = "address") // 'address' 테이블과 매핑됨을 명시
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle 사용시 Sequence 로 바꾸기
    private Long id;

    // 우편번호
    @Column(name = "postcode")
    private String postcode;

    // address
    @Column(name = "address")
    private String address;

    // detailAddress
    @Column(name = "detail_address")
    private String detailAddress;
}
