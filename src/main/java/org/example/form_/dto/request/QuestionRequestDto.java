package org.example.form_.dto.request;

import java.util.List;

public record QuestionRequestDto(
        String text,
        Long sequence,
        List<ChoiceRequestDto> options
) {}