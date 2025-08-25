package com.busanit501.translationproject.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberDTO {
    private Long Id;
    private String memberId;
    private String userName;
    private String email;
    private String password;

    public MemberDTO( String memberId, String userName, String email, String password) {
        this.memberId = memberId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
