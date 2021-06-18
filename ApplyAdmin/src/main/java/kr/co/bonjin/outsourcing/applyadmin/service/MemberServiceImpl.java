package kr.co.bonjin.outsourcing.applyadmin.service;

import kr.co.bonjin.outsourcing.applyadmin.entity.Member;
import kr.co.bonjin.outsourcing.applyadmin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /**
     * 전체 조회 - 페이징
     * @param pageable
     * @return
     */
    @Override
    public Page<Member> findAllPageableMembers(Pageable pageable) {

        int page = (pageable.getPageNumber() == 0) ? 0: (pageable.getPageNumber()-1); // page는 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");

        return memberRepository.findAll(pageable);
    }

    /**
     * 전체 조회
     * @return
     */
    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * 한건 조회
     * @param id
     * @return
     */
    @Override
    public Member findById(Long id) {
        return null;
    }

}