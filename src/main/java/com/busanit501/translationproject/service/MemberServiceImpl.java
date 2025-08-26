package com.busanit501.translationproject.service;

import com.busanit501.translationproject.domain.Member;
import com.busanit501.translationproject.repository.MemberRepository;

public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepsoitory;
    @Override
    public void register(Member member) {
        if(memberRepsoitory.memberExists(member.getMemberId()))
        {
            throw new RuntimeException("아이디가 이미 존재합니다.");
        }
        Member encodeMember = Member.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .userName(member.getUserName())
                .email(member.getEmail())
                .build();
        memberRepsoitory.save(encodeMember);
    }

    @Override
    public boolean isRegistered(String memberId) {
        boolean isRegistered = memberRepsoitory.memberExists(memberId);
        return isRegistered;
    }
}
