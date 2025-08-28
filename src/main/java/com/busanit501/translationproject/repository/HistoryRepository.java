package com.busanit501.translationproject.repository;

import com.busanit501.translationproject.domain.History;
import com.busanit501.translationproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    // 특정 사용자의 모든 기록을 최신순으로 조회
    List<History> findByMemberOrderByTimestampDesc(Member member);
}