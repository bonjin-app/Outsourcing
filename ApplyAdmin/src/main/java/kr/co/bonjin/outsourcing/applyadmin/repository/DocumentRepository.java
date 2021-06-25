package kr.co.bonjin.outsourcing.applyadmin.repository;

import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByPhone(String phone);

    List<Document> findByNameContainsOrPhoneContains(String name, String phone);

    List<Document> findByNameContainsOrPhoneContainsAndCreatedDateBetween(String name, String phone, LocalDateTime before, LocalDateTime after);

    List<Document> findByCreatedDateBetween(LocalDateTime before, LocalDateTime after);

    Page<Document> findByNameContainsOrPhoneContains(String name, String phone, Pageable pageable);

    Page<Document> findByNameContainsOrPhoneContainsAndCreatedDateBetween(String name, String phone, LocalDateTime before, LocalDateTime after, Pageable pageable);

    Page<Document> findByCreatedDateBetween(LocalDateTime before, LocalDateTime after, Pageable pageable);

    /**
     * 한건 삭제
     * @param id
     * @return
     */
    void deleteById(Long id);
}
