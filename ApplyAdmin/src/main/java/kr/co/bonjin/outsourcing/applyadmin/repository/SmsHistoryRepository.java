package kr.co.bonjin.outsourcing.applyadmin.repository;

import kr.co.bonjin.outsourcing.applyadmin.entity.SmsHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsHistoryRepository extends JpaRepository<SmsHistory, Long> {

    SmsHistory findByMessageId(String messageId);

    Page<SmsHistory> findByReceiverContains(String phone, Pageable pageable);
}
