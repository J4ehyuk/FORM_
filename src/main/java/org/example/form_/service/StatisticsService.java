package org.example.form_.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.form_.dto.response.HoverStatsDto;
import org.example.form_.dto.response.OptionHoverStats;
import org.example.form_.dto.Statistic.SelectionChangeResponse;
import org.example.form_.dto.response.ClickPerParticipantDto;
import org.example.form_.dto.response.ClickStatsResponse;
import org.example.form_.dto.response.IdlePeriodStatsDto;
import org.example.form_.entity.EventLog;
import org.example.form_.repository.EventLogRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final EventLogRepository eventLogRepository;
    private final ObjectMapper objectMapper;

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

    /**
     * 특정 이벤트의 payload에서 duration(ms) 리스트 추출
     * @param questionId 대상 문항
     * @param eventType  저장된 event_type (e.g. "question_time", "idle_period", "hover")
     * @param rootKey    payload 루트 키 (e.g. "question_time", "idle_period", "hover")
     */
    private List<Long> extractDurations(Long questionId, String eventType, String rootKey) {
        List<EventLog> logs = eventLogRepository.findByQuestion_QuestionIdAndEventType(questionId, eventType);
        return logs.stream()
                .map(log -> {
                    try {
                        JsonNode node = objectMapper.readTree(log.getPayLoad())
                                .path(rootKey)
                                .path("duration");
                        if (node.isMissingNode() || !node.isNumber()) return null;
                        long v = node.asLong();
                        return v > 0 ? v : null; // 0/음수 제외
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    /*
        특정 질문에 대한 "평균 소요 시간" (상하위 10% 절사 평균)
     */
    public Double calculateAverageDurationForQuestion(Long questionId){
        List<Long> durations = extractDurations(questionId, "question_time", "question_time");
        if (durations.isEmpty()) return null;
        return calculateTrimmedAverage(durations, 0.1);
    }

    /*
        특정 질문에 대한 "최대 소요 시간"
     */
    public Long calculateMaxDurationForQuestion(Long questionId){
        List<Long> durations = extractDurations(questionId, "question_time", "question_time");
        if (durations.isEmpty()) return null;
        return durations.stream().mapToLong(Long::longValue).max().orElseThrow();
    }

    /*
        특정 질문에 대한 "최소 소요 시간"
     */
    public Long calculateMinDurationForQuestion(Long questionId){
        List<Long> durations = extractDurations(questionId, "question_time", "question_time");
        if (durations.isEmpty()) return null;
        return durations.stream().mapToLong(Long::longValue).min().orElseThrow();
    }

    // [항목당 선택지 변경 횟수 평균] = selection_change 수 / 참여자 수(question_time 수)
    public SelectionChangeResponse getChangeResponse(Long questionId) {
        long changeCount = eventLogRepository.countByQuestion_QuestionIdAndEventType(questionId, "selection_change");
        long participantCount = eventLogRepository.countByQuestion_QuestionIdAndEventType(questionId, "question_time");

        double result = (participantCount == 0)
                ? 0.0
                : (double) changeCount / participantCount;

        return SelectionChangeResponse.fromEntity(questionId, result);
    }

    // hover 시간 통계 (optionId별 min/max/avg/trimmedAvg)
    public HoverStatsDto getHoverStats(Long questionId) {
        List<EventLog> logs = eventLogRepository.findByQuestion_QuestionIdAndEventType(questionId, "hover");

        // 옵션별 hover duration 모음: Map<optionId, List<duration>>
        Map<String, List<Long>> optionDurationMap = new HashMap<>();

        for (EventLog log : logs) {
            try {
                Map<String, Object> payloadMap = objectMapper.readValue(
                        log.getPayLoad(), new TypeReference<>() {});
                Map<String, Object> hoverMap = (Map<String, Object>) payloadMap.get("hover");
                if (hoverMap == null) continue;

                Object optionIdObj = hoverMap.get("optionId");
                Object durationObj = hoverMap.get("duration");
                if (optionIdObj == null || durationObj == null) continue;

                String optionId = optionIdObj.toString();
                Long duration = Long.valueOf(durationObj.toString());
                if (duration <= 0) continue;

                optionDurationMap.computeIfAbsent(optionId, k -> new ArrayList<>()).add(duration);
            } catch (Exception ignore) {}
        }

        List<OptionHoverStats> optionStats = optionDurationMap.entrySet().stream()
                .map(entry -> {
                    String optionId = entry.getKey();
                    List<Long> durations = entry.getValue();
                    if (durations.isEmpty()) {
                        return new OptionHoverStats(optionId, null, null, null, null);
                    }
                    Long min = durations.stream().mapToLong(Long::longValue).min().orElse(0L);
                    Long max = durations.stream().mapToLong(Long::longValue).max().orElse(0L);
                    Double avg = durations.stream().mapToLong(Long::longValue).average().orElse(0.0);
                    Double trimmed = calculateTrimmedAverage(durations, 0.1);
                    return new OptionHoverStats(optionId, min, max, avg, trimmed);
                })
                .toList();

        return new HoverStatsDto(questionId, optionStats);
    }

    // idle_period 통계 (전체 min/max/avg/trimmedAvg + count)
    public IdlePeriodStatsDto getIdlePeriodStats(Long questionId) {
        List<EventLog> logs = eventLogRepository.findByQuestion_QuestionIdAndEventType(questionId, "idle_period");

        List<Long> durations = logs.stream()
                .map(log -> {
                    try {
                        Map<String, Object> payloadMap = objectMapper.readValue(
                                log.getPayLoad(), new TypeReference<Map<String, Object>>() {});
                        Map<String, Object> idlePeriodMap = (Map<String, Object>) payloadMap.get("idle_period");
                        if (idlePeriodMap == null) return null;

                        Object durationObj = idlePeriodMap.get("duration");
                        if (durationObj == null) return null;

                        Long d = Long.valueOf(durationObj.toString());
                        return d > 0 ? d : null;
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

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

        long min = durations.stream().mapToLong(Long::longValue).min().orElseThrow();
        long max = durations.stream().mapToLong(Long::longValue).max().orElseThrow();
        double avg = durations.stream().mapToLong(Long::longValue).average().orElse(0.0);
        double tAvg = calculateTrimmedAverage(durations, 0.1);

        return IdlePeriodStatsDto.builder()
                .questionId(questionId)
                .count((long) durations.size())
                .minDuration(min)
                .maxDuration(max)
                .avgDuration(avg)
                .trimmedAverageDuration(tAvg)
                .build();
    }

    // 옵션별 평균 클릭 수 = (optionId별 click 수) / participant(question_time)
    public ClickStatsResponse getOptionClickStats(Long questionId) {
        List<EventLog> clickLogs = eventLogRepository.findByQuestion_QuestionIdAndEventType(questionId, "click");
        long participantCount = eventLogRepository.countByQuestion_QuestionIdAndEventType(questionId, "question_time");

        Map<String, Long> optionClickCount = new HashMap<>();

        for (EventLog log : clickLogs) {
            try {
                Map<String, Object> payloadMap = objectMapper.readValue(
                        log.getPayLoad(), new TypeReference<>() {});
                Map<String, Object> clickMap = (Map<String, Object>) payloadMap.get("click");
                if (clickMap == null) continue;

                Object opt = clickMap.get("selectedOptionId");
                if (opt == null) continue;

                String optionId = opt.toString();
                optionClickCount.merge(optionId, 1L, Long::sum);
            } catch (Exception ignore) {}
        }

        List<ClickPerParticipantDto> stats = optionClickCount.entrySet().stream()
                .map(entry -> {
                    long clicks = entry.getValue();
                    double avg = (participantCount == 0) ? 0.0 : (double) clicks / participantCount;
                    return ClickPerParticipantDto.builder()
                            .optionId(entry.getKey())             // DTO에 optionId 필드가 있어야 함
                            .clickCount(clicks)
                            .participantCount(participantCount)
                            .clickPerParticipant(avg)
                            .build();
                })
                .toList();

        return ClickStatsResponse.builder()
                .questionId(questionId)
                .optionStats(stats)
                .build();
    }
}