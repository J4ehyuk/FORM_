package org.example.form_.dto.response;

public record ChoiceCountResponseDto(
        Long choiceId,
        String choiceText,
        Long count
) {}