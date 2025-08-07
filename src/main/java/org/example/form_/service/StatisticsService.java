package org.example.form_.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.form_.dto.Statistic.SelectionChangeResponse;
import org.example.form_.entity.EventLog;
import org.example.form_.repository.EventLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

  private final EventLogRepository eventLogRepository;
  private final ObjectMapper objectMapper;

  // [항목당 선택지 변경 횟수 반환]
  // 항목 아이디를 매개변수로 받아옴
  public SelectionChangeResponse getChangeResponse(@PathVariable Long questionId) {
    Long count = eventLogRepository.countByQuestion_QuestionIdAndEventType(questionId, "selection_change");

    return SelectionChangeResponse.fromEntity(questionId, count);
  }



  // "특정 질문"에 대한 "평균 소요 시간" 계산 (절사 평균 값)
  public long calculateAverageDurationForQuestion(Long questionId) {
    eventLogRepository.countByQuestion_QuestionIdAndEventType(questionId, "question_time");

  }









}
