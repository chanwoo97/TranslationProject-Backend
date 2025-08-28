package com.busanit501.translationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {
    private Long id;
    private String toolType;
    private String inputText;
    private String outputText;
    private LocalDateTime timestamp;
}