package com.busanit501.translationproject.service;

import com.busanit501.translationproject.domain.History;
import com.busanit501.translationproject.domain.Member;
import com.busanit501.translationproject.dto.HistoryDTO;
import com.busanit501.translationproject.repository.HistoryRepository;
import com.busanit501.translationproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final MemberRepository memberRepository;

    // 작업 내역 저장
    public void saveHistory(String memberId, String toolType, String inputText, String outputText) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + memberId));

        History history = History.builder()
                .member(member)
                .toolType(toolType)
                .inputText(inputText)
                .outputText(outputText)
                .build();
        historyRepository.save(history);
    }

    // 특정 사용자의 작업 내역 조회
    @Transactional(readOnly = true)
    public List<HistoryDTO> getHistoryForUser(String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + memberId));

        List<History> histories = historyRepository.findByMemberOrderByTimestampDesc(member);

        return histories.stream()
                .map(history -> HistoryDTO.builder()
                        .id(history.getId())
                        .toolType(history.getToolType())
                        .inputText(history.getInputText())
                        .outputText(history.getOutputText())
                        .timestamp(history.getTimestamp())
                        .build())
                .collect(Collectors.toList());
    }
}