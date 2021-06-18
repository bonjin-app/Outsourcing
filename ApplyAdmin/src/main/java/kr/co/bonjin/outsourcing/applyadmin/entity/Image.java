package kr.co.bonjin.outsourcing.applyadmin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image_file")   // 첨부파일
public class Image {

    @Id
    @Column(name = "image_file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle 사용시 Sequence 로 바꾸기
    private Long id;

    // 파일 아이디
    @Column(name = "file_no")
    private String fileNo;

    // 파일명
    @Column(name = "file_nm")
    private String fileNm;

    // 암호화 파일명
    @Column(name = "save_file_nm")
    private String saveFileNm;

    // 파일확장자
    @Column(name = "file_extn")
    private String fileExtn;
    
    // 파일크기
    @Column(name = "fise_size")
    private Long fileSize;

    // 저장경로
    @Column(name = "save_path", length = 500)
    private String savePath;

    // 파일 데이터
    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

}
