package org.example.form_.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.form_.dto.SankeyLinkDto;
import org.example.form_.entity.EventLog;
import org.example.form_.repository.EventLogRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SankeyDataService {

    private final EventLogRepository eventLogRepository;
    private final ObjectMapper objectMapper;

    public List<SankeyLinkDto> getSankeyLinksByQuestion(Long questionId) {
        List<EventLog> logs = eventLogRepository.findByQuestion_QuestionId(questionId);

        Map<String, Integer> linkCountMap = new HashMap<>();

        for (EventLog log : logs) {
            if (!"selection_change".equals(log.getEventType())) continue;

            try {
                JsonNode payload = objectMapper.readTree(log.getPayLoad());

                String from = payload.path("from").asText(null);
                String to = payload.path("to").asText(null);

                // null이거나 공백이면 제외
                if (from == null || from.isBlank() || to == null || to.isBlank()) continue;

                String fromWithDot = from + ".";

                String key = fromWithDot + "->" + to;
                linkCountMap.put(key, linkCountMap.getOrDefault(key, 0) + 1);

            } catch (Exception e) {
                e.printStackTrace(); // 개발 중에는 출력, 운영 환경에서는 로깅 처리
            }
        }

        return linkCountMap.entrySet().stream()
                .map(entry -> {
                    String[] keys = entry.getKey().split("->");
                    return new SankeyLinkDto(keys[0], keys[1], entry.getValue());
                })
                .toList();
    }
}
