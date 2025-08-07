package org.example.form_.dto.Statistic;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AverageDurationQuestionResponse {
  private Long questionId;
  private Double averageDurationMs; // 절사평균
}