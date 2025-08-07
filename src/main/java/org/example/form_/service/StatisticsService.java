package org.example.form_.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.form_.dto.Statistic.SelectionChangeResponse;
import org.example.form_.dto.response.IdlePeriodStatsDto;
import org.example.form_.entity.EventLog;
import org.example.form_.repository.EventLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatisticsService {

  private final EventLogRepository eventLogRepository;

  // 절사 평균 계산기
  private Double calculateTrimmedAverage(List<Long> values, double trimRatio) {
    List<Long> sorted = values.stream().sorted().toList();
    int total = sorted.size();
    int trim = (int) Math.floor(total * trimRatio);

    if (total - 2 * trim < 1) {
      return values.stream().mapToLong(Long::longValue).average().orElse(0.0);
    }

    return sorted.subList(trim, total - trim).stream()
            .mapToLong(Long::longValue)
            .average()
            .orElse(0.0);
  }

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

  public IdlePeriodStatsDto getIdlePeriodStats(Long questionId) {
    List<EventLog> logs = eventLogRepository.findByQuestion_QuestionIdAndEventType(questionId, "idle_period");

    ObjectMapper objectMapper = new ObjectMapper();

    List<Long> durations = logs.stream()
            .map(log -> {
              try {
                Map<String, Object> payloadMap = objectMapper.readValue(
                        log.getPayLoad(), new TypeReference<Map<String, Object>>() {}
                );
                Map<String, Object> idlePeriodMap = (Map<String, Object>) payloadMap.get("idle_period");
                if (idlePeriodMap == null) return null;

                Object durationObj = idlePeriodMap.get("duration");
                if (durationObj == null) return null;

                return Long.valueOf(durationObj.toString());

              } catch (Exception e) {
                return null;
              }
            })
            .filter(Objects::nonNull)
            .toList();

    // 유요한 결과가 없으면 null 기반 응답 반환
    if (durations.isEmpty()) {
      return IdlePeriodStatsDto.builder()
              .questionId(questionId)
              .count(0L)
              .minDuration(null)
              .maxDuration(null)
              .avgDuration(null)
              .trimmedAverageDuration(null)
              .build();
    }

    Long min = Collections.min(durations);
    Long max = Collections.max(durations);
    Double avg = durations.stream().mapToLong(Long::longValue).average().orElse(0);
    Double tAvg = calculateTrimmedAverage(durations, 0.1);

    return IdlePeriodStatsDto.builder()
            .questionId(questionId)
            .count((long) durations.size())
            .minDuration(min)
            .maxDuration(max)
            .avgDuration(avg)
            .trimmedAverageDuration(tAvg)
            .build();
  }



}
