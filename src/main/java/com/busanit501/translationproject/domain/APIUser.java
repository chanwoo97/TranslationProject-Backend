package com.busanit501.translationproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class APIUser {

    @Id // Primary Key 설정
    private String memberId; // 사용자 ID

    private String password; // 사용자 비밀번호

    // 비밀번호 변경 메서드
    public void changePw(String mpw) {
        this.password = mpw;
    }
}
