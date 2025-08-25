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
    private String mid; // 사용자 ID
    private String mpw; // 사용자 PassWord
}