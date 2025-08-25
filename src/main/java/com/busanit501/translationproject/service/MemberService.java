package com.busanit501.translationproject.service;

import com.busanit501.translationproject.domain.Member;

public interface MemberService {
    void register(Member member);
    boolean isRegistered(String memberId);
}
