package org.example.form_.service;


import lombok.RequiredArgsConstructor;
import org.example.form_.dto.Statistic.SelectionChangeResponse;
import org.example.form_.repository.EventLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class StatisticsService {

  private final EventLogRepository eventLogRepository;

  // - 선택지별로 클릭수 -> ChoiceCountService 에서 제공
  // - 선택지별 최소
  // 설문 id에 해당하는 모든 choice_count 에 대해서, 각 sqeuence별
  // 선택지별 최대
  // 선택지별 평균



  // [항목당 선택지 변경 횟수 반환]
  // 항목 아이디를 매개변수로 받아옴
  public SelectionChangeResponse getChangeResponse(@PathVariable Long questionId) {
    Long count = eventLogRepository.countByQuestion_QuestionIdAndEventType(questionId, "selection_change");

    return SelectionChangeResponse.fromEntity(questionId, count);
  }





}
