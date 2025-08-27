package com.busanit501.translationproject.service;

import com.busanit501.translationproject.domain.Member;
import com.busanit501.translationproject.dto.ChangePasswordRequestDTO;
import com.busanit501.translationproject.dto.MemberDTO;

public interface MemberService {
    void register(Member member);
    boolean isRegistered(String memberId);
    void join(MemberDTO memberDTO);
    boolean checkId(String memberId);
    MemberDTO getMember(String memberId);
    void changePassword(String memberId, ChangePasswordRequestDTO requestDTO);
}

