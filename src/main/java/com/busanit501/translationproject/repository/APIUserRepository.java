package com.busanit501.translationproject.repository;

import com.busanit501.translationproject.domain.APIUser;
import com.busanit501.translationproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface APIUserRepository extends JpaRepository<APIUser, String> {
    boolean existsByMemberId(String memberId);
    Optional<APIUser> findByMemberId(String memberId);
}
