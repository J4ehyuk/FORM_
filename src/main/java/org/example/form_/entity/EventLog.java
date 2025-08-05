package org.example.form_.entity;


import io.swagger.v3.core.util.Json;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EventLog { // 각 설문의 항목에 대한 이벤트 로그

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long eventLogId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id", nullable = false)
  private Question question; // 문항 ID

  @Column(nullable = false)
  private String eventType; // 이벤트 유형 (hover, click 등)

  @Column(nullable = false)
  private LocalDateTime timestamp_ms; // 이벤트 발생 시각 (UNIX ms)

  @Lob
  @Column(columnDefinition = "json") // 이벤트별 상세 데이터 (optionId, duration 등)
  private String payLoad;
}
