package kr.co.bonjin.outsourcing.applyadmin.repository;

import kr.co.bonjin.outsourcing.applyadmin.entity.SmsHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SmsHistoryRepository extends JpaRepository<SmsHistory, Long> {

    SmsHistory findByMessageId(String messageId);

    Page<SmsHistory> findByReceiverContains(String phone, Pageable pageable);

    Page<SmsHistory> findByReceiverContainsAndCreatedDateBetween(String phone, LocalDateTime before, LocalDateTime after, Pageable pageable);

    Page<SmsHistory> findByCreatedDateBetween(LocalDateTime before, LocalDateTime after, Pageable pageable);
}
