package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.HistoryDTO;
import com.busanit501.translationproject.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public ResponseEntity<List<HistoryDTO>> getUserHistory() {
        // 현재 로그인한 사용자의 ID를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();

        List<HistoryDTO> historyList = historyService.getHistoryForUser(memberId);
        return ResponseEntity.ok(historyList);
    }
}