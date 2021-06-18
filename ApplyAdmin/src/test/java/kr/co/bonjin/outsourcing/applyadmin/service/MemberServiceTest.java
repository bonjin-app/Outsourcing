package kr.co.bonjin.outsourcing.applyadmin.service;

import kr.co.bonjin.outsourcing.applyadmin.entity.Member;
import kr.co.bonjin.outsourcing.applyadmin.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Rollback(false)
    void 유저생성_TEST() {
        Member member = Member.builder()
                .username("admin")
                .nickname("BONJIN")
                .email("bonjin.app@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberRepository.save(member);
    }
}