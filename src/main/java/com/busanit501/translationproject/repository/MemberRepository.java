package com.busanit501.translationproject.repository;

import com.busanit501.translationproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByMemberId(String memberId);
    Optional<Member> findByMemberId(String memberId);
}
