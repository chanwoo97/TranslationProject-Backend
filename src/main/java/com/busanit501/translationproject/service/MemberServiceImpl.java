package com.busanit501.translationproject.service;

import com.busanit501.translationproject.domain.Member;
import com.busanit501.translationproject.dto.MemberDTO;
import com.busanit501.translationproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    @Override
    public void register(Member member) {
        if(memberRepository.existsByMemberId(member.getMemberId()))
        {
            throw new RuntimeException("아이디가 이미 존재합니다.");
        }
        Member encodeMember = Member.builder()
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .userName(member.getUserName())
                .email(member.getEmail())
                .build();
        memberRepository.save(encodeMember);
    }

    @Override
    public boolean isRegistered(String memberId) {
        boolean isRegistered = memberRepository.existsByMemberId(memberId);
        return isRegistered;
    }

    @Override
    public void join(MemberDTO  memberDTO) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());

        // DTO -> 엔티티 변환
        Member member = Member.builder()
                .memberId(memberDTO.getMemberId()) // 아이디
                .password(encodedPassword) // 암호화된 비밀번호
                .userName(memberDTO.getUserName()) // 이름
                .email(memberDTO.getEmail()) // 이메일
                .role("USER")
                .social(false)
                .build();

        // DB 저장
        memberRepository.save(member);
        log.info("회원가입 완료 : " + member.getMemberId());

    }
}
