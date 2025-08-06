package org.example.form_.dto.request;

import java.util.List;

public record SurveyRequestDto(
        String title,
        String subTitle,
        List<QuestionRequestDto> questions
) {}
