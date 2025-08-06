package org.example.form_.repository;

import org.example.form_.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // 특정 Survey에 속한 Question 리스트 조회
    List<Question> findBySurvey_SurveyIdOrderBySequence(Long surveyId);
}
