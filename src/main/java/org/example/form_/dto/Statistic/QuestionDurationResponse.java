package org.example.form_.dto.Statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionDurationResponse {
  private Long questionId;
  private long totalDurationMs;
}