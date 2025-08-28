package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.HistoryDTO;
import com.busanit501.translationproject.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable("id") Long id) {
        // 현재 로그인한 사용자의 ID를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();

        try {
            // 서비스를 호출하여 삭제를 시도합니다. (내부적으로 권한 검사 수행)
            historyService.deleteHistory(id, memberId);
            // 성공 시, 내용 없이 204 상태 코드로 응답합니다.
            return ResponseEntity.noContent().build();
        } catch (AccessDeniedException e) {
            // 권한이 없는 경우 403 Forbidden 상태 코드로 응답합니다.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalArgumentException e) {
            // ID에 해당하는 기록이 없는 경우 404 Not Found 상태 코드로 응답합니다.
            return ResponseEntity.notFound().build();
        }
    }
}