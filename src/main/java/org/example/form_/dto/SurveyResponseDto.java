package org.example.form_.dto;

import org.example.form_.entity.Survey;

import java.util.List;

public record SurveyResponseDto(Long surveyId, String title, List<QuestionResponseDto> questions) {
    public static SurveyResponseDto fromEntity(Survey survey) {
        List<QuestionResponseDto> questions = survey.getQuestions().stream()
                .map(QuestionResponseDto::fromEntity)
                .toList();

        return new SurveyResponseDto(survey.getSurveyId(), survey.getTitle(), questions);
    }
}
