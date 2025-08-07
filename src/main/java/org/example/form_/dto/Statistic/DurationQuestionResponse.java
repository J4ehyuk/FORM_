package org.example.form_.dto.Statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DurationQuestionResponse {
  private Long questionId;
  private Long value; // 최대값 혹은 최소값
}
