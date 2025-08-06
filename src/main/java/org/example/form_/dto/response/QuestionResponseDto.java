package org.example.form_.dto.response;

import org.example.form_.entity.Question;

import java.util.List;

public record QuestionResponseDto(Long questionId, String text, Long sequence, List<ChoiceResponseDto> options) {
    public static QuestionResponseDto fromEntity(Question question) {
        List<ChoiceResponseDto> choices = question.getOptions().stream()
                .map(ChoiceResponseDto::fromEntity)
                .toList();

        return new QuestionResponseDto(question.getQuestionId(), question.getText(), question.getSequence(), choices);
    }
}