package org.example.form_.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class EventLogResponseDto {
  private Long eventLogId;
  private Long questionId;
  private String eventType;
  private LocalDateTime timestamp_ms;
  private Map<String, Object> payLoad;
}
