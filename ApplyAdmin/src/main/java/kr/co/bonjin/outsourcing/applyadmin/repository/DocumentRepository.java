package kr.co.bonjin.outsourcing.applyadmin.repository;

import kr.co.bonjin.outsourcing.applyadmin.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByPhone(String phone);

    Page<Document> findByNameContainsOrPhoneContains(String name, String phone, Pageable pageable);
}
