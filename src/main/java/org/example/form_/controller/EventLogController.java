package org.example.form_.controller;


import lombok.RequiredArgsConstructor;
import org.example.form_.dto.request.EventLogRequestDto;
import org.example.form_.dto.response.EventLogResponseDto;
import org.example.form_.service.EventLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event-logs")
public class EventLogController {

  private final EventLogService eventLogService;

  // 해당 question 이벤트 로그 저장
  @PostMapping
  public ResponseEntity<Void> saveEventLog(@RequestBody EventLogRequestDto dto) {
    eventLogService.saveEventLog(dto);
    return ResponseEntity.ok().build();
  }

  // 해당 question 이벤트 로그 조회
  @GetMapping("/question/{questionId}")
  public ResponseEntity<List<EventLogResponseDto>> getLogs(@PathVariable Long questionId) {
    return ResponseEntity.ok(eventLogService.getLogsByQuestionId(questionId));
  }

}
