package org.example.form_.repository;

import org.example.form_.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    // 특정 Question에 속한 Choice 리스트 조회
    List<Choice> findByQuestion_QuestionIdOrderBySequence(Long questionId);
}
