package com.busanit501.translationproject.repository;

import com.busanit501.translationproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean memberExists(String memberId);
}
