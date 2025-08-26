package com.busanit501.translationproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    @Id
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(name = "userName")
    private String userName;

    @Column(name = "email")
    private String email;
}
