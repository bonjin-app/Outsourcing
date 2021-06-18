package kr.co.bonjin.outsourcing.applyadmin.service;

import kr.co.bonjin.outsourcing.applyadmin.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {
    /**
     * 전체 조회 - 페이징
     * @param pageable
     * @return
     */
    Page<Member> findAllPageableMembers(Pageable pageable);


    /**
     * 전체 조회
     * @return
     */
    List<Member> findAllMembers();


    /**
     * 한건 조회
     * @param id
     * @return
     */
    Member findById(Long id);

}
