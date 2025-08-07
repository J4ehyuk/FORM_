package org.example.form_.controller;


import lombok.RequiredArgsConstructor;
import org.example.form_.dto.Statistic.QuestionDurationResponse;
import org.example.form_.dto.Statistic.SelectionChangeResponse;
import org.example.form_.service.EventLogService;
import org.example.form_.service.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
public class StatisticController {

  private final StatisticsService  statisticsService;

  // 특정 질문의 선택지 변경 횟수
  @GetMapping("/selection-change/{questionId}")
  public ResponseEntity<SelectionChangeResponse> getSelectionChange(@PathVariable Long questionId) {
    SelectionChangeResponse result = statisticsService.getChangeResponse(questionId);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  // 특정 질문의 총 소요 시간
  @GetMapping("/question_time/{questionId}")
  public ResponseEntity<QuestionDurationResponse> getQuestionDuration(@PathVariable Long questionId) {
    // statisticsService를 사용하여 총 소요 시간(ms)을 계산
    long totalDurationMs = statisticsService.calculateTotalDurationForQuestion(questionId);

    // 응답 DTO를 생성
    QuestionDurationResponse response = new QuestionDurationResponse(questionId, totalDurationMs);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }








}
