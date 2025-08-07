package org.example.form_.repository;

import org.example.form_.dto.response.ChoiceCountResponseDto;
import org.example.form_.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
  // 질문 id로 로그 검색
  List<EventLog> findByQuestion_QuestionId(Long questionId);

  // 현재 질문 id의 항목 변경 횟수 카운트
  Long countByQuestion_QuestionIdAndEventType(Long questionId, String eventType);

  // 질문 id + 이벤트 명으로 검색
  List<EventLog> findByQuestion_QuestionIdAndEventType(Long questionId, String eventType);
}
