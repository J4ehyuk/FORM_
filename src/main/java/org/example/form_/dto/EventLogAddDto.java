package org.example.form_.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class EventLogAddDto { // 로그 저장 요청 dto
  private Long questionId;
  private String eventType;
  private LocalDateTime timestamp_ms;
  private Map<String, Object> payLoad; // json 받기 위함
}
