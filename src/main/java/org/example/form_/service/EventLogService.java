package org.example.form_.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.form_.dto.request.EventLogRequestDto;
import org.example.form_.dto.response.EventLogResponseDto;
import org.example.form_.entity.EventLog;
import org.example.form_.entity.Question;
import org.example.form_.repository.EventLogRepository;
import org.example.form_.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventLogService {
  private final EventLogRepository eventLogRepository;
  private final QuestionRepository questionRepository;

  private final ObjectMapper objectMapper; // jackson의 ObjectMapper 주입

  /*
    로그 저장
   */
  public void saveEventLog(EventLogRequestDto dto) {
    Question question = questionRepository.findById(dto.getQuestionId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문 ID"));

    String payLoadJson;
    try{
      payLoadJson = objectMapper.writeValueAsString(dto.getPayLoad()); // json -> string

    }catch (JsonProcessingException e){
      throw new RuntimeException("payload JSON 변환 실패", e);
    }

    EventLog eventLog = EventLog.builder()
            .question(question)
            .eventType(dto.getEventType())
            .timestamp_ms(dto.getTimestamp_ms())
            .payLoad(payLoadJson)
            .build();

    eventLogRepository.save(eventLog);
  }

  /*
  questionId로 로그 조회
   */

  public List<EventLogResponseDto> getLogsByQuestionId(Long questionId) {
    List<EventLog> logs = eventLogRepository.findByQuestion_QuestionId(questionId);

    return logs.stream().map(log -> {
      Map<String, Object> payLoadMap = null;
      try {
        payLoadMap = objectMapper.readValue(
                log.getPayLoad(),
                new TypeReference<Map<String, Object>>() {}
        );
      } catch (Exception e) {
        // 파싱 실패 시 로그 찍거나 null 처리
        e.printStackTrace();
      }

      return EventLogResponseDto.builder()
              .eventLogId(log.getEventLogId())
              .questionId(log.getQuestion().getQuestionId())
              .eventType(log.getEventType())
              .timestamp_ms(log.getTimestamp_ms())
              .payLoad(payLoadMap)
              .build();
    }).collect(Collectors.toList());
  }



}
