package kr.co.bonjin.outsourcing.applyadmin.service;

import kr.co.bonjin.outsourcing.applyadmin.entity.Address;
import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import kr.co.bonjin.outsourcing.applyadmin.repository.DocumentRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
@Transactional
class DocumentServiceTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    @Rollback(false)
    void 문서생성_TEST() {

        IntStream.range(1, 100)
                .forEach(e -> {
                    Address address = Address.builder()
                            .address("경기도 성남시 분당구 KR 대왕판교로 670")
                            .detailAddress("KR 유스페이스 2 1층")
                            .postcode("13494")
                            .build();

                    Document document = Document.builder()
                            .name("GIGAS")
                            .phone("01050545654")
                            .residentId("1234561234567")
                            .gender("남자")
                            .address(address)
                            .build();

                    documentRepository.save(document);
                });
    }

    @Test
    @Disabled
    @Rollback(false)
    void 삭제_TEST() {
        documentRepository.deleteById(118L);
    }
}