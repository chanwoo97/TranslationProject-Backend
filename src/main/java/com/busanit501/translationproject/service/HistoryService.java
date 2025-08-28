package com.busanit501.translationproject.service;

import com.busanit501.translationproject.domain.History;
import com.busanit501.translationproject.domain.Member;
import com.busanit501.translationproject.dto.HistoryDTO;
import com.busanit501.translationproject.repository.HistoryRepository;
import com.busanit501.translationproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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

    public void deleteHistory(Long historyId, String memberId) {
        // 1. ID로 히스토리 항목을 찾습니다.
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 히스토리를 찾을 수 없습니다: " + historyId));

        // 2. 히스토리의 소유자 ID와 요청한 사용자의 ID가 일치하는지 확인합니다.
        if (!history.getMember().getMemberId().equals(memberId)) {
            // 일치하지 않으면 권한 없음 예외를 발생시킵니다.
            throw new AccessDeniedException("이 기록을 삭제할 권한이 없습니다.");
        }

        // 3. 소유자가 맞으면 기록을 삭제합니다.
        historyRepository.delete(history);
    }

}