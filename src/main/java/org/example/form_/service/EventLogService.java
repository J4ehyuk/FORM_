package org.example.form_.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.form_.dto.EventLogAddDto;
import org.example.form_.entity.EventLog;
import org.example.form_.entity.Question;
import org.example.form_.repository.EventLogRepository;
import org.example.form_.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventLogService {
  private final EventLogRepository eventLogRepository;
  private final QuestionRepository questionRepository;

  private final ObjectMapper objectMapper; // jackson의 ObjectMapper 주입

  /*
    로그 저장
   */
  public void saveEventLog(EventLogAddDto dto) {
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



}
