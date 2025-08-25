package com.busanit501.translationproject.repository;

import com.busanit501.translationproject.domain.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APIUserRepository extends JpaRepository<APIUser, String> {
    // 아이디(mid)로 회원이 존재하는지 확인하기 위한 메소드
    boolean existsByMid(String mid);
}
