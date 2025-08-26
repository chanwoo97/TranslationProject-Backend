package com.busanit501.translationproject.repository;

import com.busanit501.translationproject.domain.APIUser;
import com.busanit501.translationproject.domain.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private APIUserRepository apiUserRepository;

    @Test
    public void testInserts(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .memberId("member"+i)
                    .password(passwordEncoder.encode("1111"))
                    .userName("user"+i)
                    .email("user"+i+"@"+"naver.com")
                    .build();
            memberRepository.save(member);
            APIUser apiUser = APIUser.builder()
                    .memberId(member.getMemberId())
                    .password(member.getPassword())
                    .build();
            apiUserRepository.save(apiUser);

        });
    }
}
