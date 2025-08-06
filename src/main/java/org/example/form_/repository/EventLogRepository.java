package org.example.form_.repository;

import org.example.form_.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
  List<EventLog> findByQuestion_QuestionId(Long questionId);
}
