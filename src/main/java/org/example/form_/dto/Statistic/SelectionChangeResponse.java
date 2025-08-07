package org.example.form_.dto.Statistic;

public record SelectionChangeResponse(
        Long questionId,
        Long changeCount // 변경 횟수
) {
  public static SelectionChangeResponse fromEntity(Long questionId, Long changeCount) {
    return new SelectionChangeResponse(questionId, changeCount);
  }
}
