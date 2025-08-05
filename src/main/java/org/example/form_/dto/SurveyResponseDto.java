package org.example.form_.dto;

import java.util.List;

public record SurveyResponseDto(
    Long surveyId,
    String title,
    List<QuestionResponseDto> questions
){}
