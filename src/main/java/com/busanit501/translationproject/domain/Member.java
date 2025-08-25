package com.busanit501.translationproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(name = "userName")
    private String userName;

    @Column(name = "email")
    private String email;
}
