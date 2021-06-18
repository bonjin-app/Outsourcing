package kr.co.bonjin.outsourcing.applyadmin.entity.common;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 공통적으로 사용
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {

    /******************
     공통적으로 사용
     ******************/
    // 생성일자
    @Column(name="created_date", updatable=false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    // 수정일자
    @Column(name="updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;
}
