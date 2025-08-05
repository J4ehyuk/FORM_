package org.example.form_.dto;

import java.util.List;

public record QuestionResponseDto(
    Long questionId,
    String text,
    Long sequence,
    List<ChoiceResponseDto> options
){}