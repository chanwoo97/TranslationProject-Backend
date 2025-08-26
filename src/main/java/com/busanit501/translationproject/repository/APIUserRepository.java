package com.busanit501.translationproject.repository;

import com.busanit501.translationproject.domain.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIUserRepository extends JpaRepository<APIUser, String> {
    boolean existsByMemberId(String memberId);
}
