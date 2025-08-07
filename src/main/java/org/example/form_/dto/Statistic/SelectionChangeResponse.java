package org.example.form_.dto.Statistic;

public record SelectionChangeResponse(
        Long questionId,
        Double avgChangeCount // 변경 횟수
) {
  public static SelectionChangeResponse fromEntity(Long questionId, Double avgChangeCount) {
    return new SelectionChangeResponse(questionId, avgChangeCount);
  }
}