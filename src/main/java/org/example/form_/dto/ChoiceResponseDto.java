package org.example.form_.dto;

import org.example.form_.entity.Choice;

public record ChoiceResponseDto(Long choiceId, String text, Long sequence) {
    public static ChoiceResponseDto fromEntity(Choice choice) {
        return new ChoiceResponseDto(choice.getChoiceId(), choice.getText(), choice.getSequence());
    }
}
