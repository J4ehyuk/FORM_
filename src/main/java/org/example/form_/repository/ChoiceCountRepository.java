package org.example.form_.repository;

import org.example.form_.dto.response.ChoiceCountResponseDto;
import org.example.form_.entity.Choice;
import org.example.form_.entity.ChoiceCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChoiceCountRepository extends JpaRepository<ChoiceCount, Long> {

    // DTO 통계 조회 쿼리
    @Query("""
        SELECT new org.example.form_.dto.response.ChoiceCountResponseDto(
            cc.choice.choiceId,
            cc.choice.text,
            cc.count
        )
        FROM ChoiceCount cc
        WHERE cc.choice.question.survey.surveyId = :surveyId
    """)
    List<ChoiceCountResponseDto> findStatsBySurveyId(@Param("surveyId") Long surveyId);

    // 일반 조회
    Optional<ChoiceCount> findByChoice(Choice choice);
}