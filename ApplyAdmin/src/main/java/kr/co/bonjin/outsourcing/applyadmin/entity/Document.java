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
@Table(name = "document") // 'document' 테이블과 매핑됨을 명시
public class Document extends BaseEntity {
    @Id
    @Column(name = "document_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle 사용시 Sequence 로 바꾸기
    private Long id;

    // 이름
    @Column(name = "name")
    private String name;

    // 주민등록번호
    @Column(name = "resident_id")
    private String residentId;

    // 핸드폰번호
    @Column(name = "phone")
    private String phone;

    // 추천인
    @Column(name = "recommender")
    private String recommender;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_file_id")
    private Image image;
}
