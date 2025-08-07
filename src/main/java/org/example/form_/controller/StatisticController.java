package org.example.form_.controller;


import lombok.RequiredArgsConstructor;
import org.example.form_.dto.response.ClickPerParticipantDto;
import org.example.form_.dto.Statistic.QuestionDurationResponse;
import org.example.form_.dto.Statistic.SelectionChangeResponse;
import org.example.form_.dto.response.IdlePeriodStatsDto;
import org.example.form_.dto.response.HoverStatsDto;
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


  // 특정 항목의 선택지 변경 횟수
  @GetMapping("/selection-change/{questionId}")
  public ResponseEntity<SelectionChangeResponse> getSelectionChange(@PathVariable Long questionId) {
    SelectionChangeResponse result = statisticsService.getChangeResponse(questionId);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  // 마우스 멈춘 시간
  @GetMapping("/idle-period/{questionId}")
  public IdlePeriodStatsDto getIdleStats(@PathVariable Long questionId) {
    return statisticsService.getIdlePeriodStats(questionId);
  }

  // 평균 클릭 수
  @GetMapping("/statistic/click-per-participant/{questionId}")
  public ResponseEntity<ClickPerParticipantDto> getClickPerParticipant(@PathVariable Long questionId) {
    return ResponseEntity.ok(statisticsService.getClickPerParticipant(questionId));
  }

  // 호버 시간 평균 최대 최소
  @GetMapping("/hover-stats/{questionId}")
  public HoverStatsDto getHoverStats(@PathVariable Long questionId) {
    return statisticsService.getHoverStats(questionId);
  }


}
