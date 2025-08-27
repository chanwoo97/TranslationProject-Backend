package com.busanit501.translationproject.service;

import com.busanit501.translationproject.domain.APIUser;
import com.busanit501.translationproject.domain.Member;
import com.busanit501.translationproject.dto.MemberDTO;
import com.busanit501.translationproject.repository.APIUserRepository;
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
    private final APIUserRepository apiUserRepository;
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
        log.info("회원가입 서비스 호출 : " + memberDTO);

        // 아이디 중복 체크
        if (memberRepository.existsByMemberId(memberDTO.getMemberId())) {
            throw  new IllegalArgumentException("이미 사용 중인 아이디입니다."); // 예외처리
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());

        // DTO -> 엔티티 변환
        Member member = Member.builder()
                .memberId(memberDTO.getMemberId()) // 아이디
                .password(encodedPassword) // 암호화된 비밀번호
                .userName(memberDTO.getUserName()) // 이름
                .email(memberDTO.getEmail()) // 이메일
                .build();

        // DB 저장
        memberRepository.save(member);
        APIUser apiUser = APIUser.builder()
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .build();
        apiUserRepository.save(apiUser);

        log.info("회원가입 완료 : " + member.getMemberId());
    }

    // 아이디 중복 체크
    @Override
    public boolean checkId(String memberId) {
        // memberId가 존재하면 true 반환(중복), 존재하지 않으면 false반환(사용가능)
        return memberRepository.existsByMemberId(memberId);
    }

    @Override
    public MemberDTO getMember(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .map(member -> new MemberDTO(
                        member.getMemberId(),
                        member.getUserName(),
                        member.getEmail(),
                        member.getPassword()))
                .orElse(null);
    }
}
