package org.example.form_.controller;


import lombok.RequiredArgsConstructor;
import org.example.form_.dto.EventLogAddDto;
import org.example.form_.service.EventLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event-logs")
public class EventLogController {

  private final EventLogService eventLogService;

  @PostMapping
  public ResponseEntity<Void> saveEventLog(@RequestBody EventLogAddDto dto) {
    eventLogService.saveEventLog(dto);
    return ResponseEntity.ok().build();
  }

}
