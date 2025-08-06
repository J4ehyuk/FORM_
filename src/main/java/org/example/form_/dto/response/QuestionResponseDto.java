package org.example.form_.dto.response;

import org.example.form_.entity.Question;

import java.util.List;

public record QuestionResponseDto(Long questionId, String content, Long sequence) {
    public static QuestionResponseDto fromEntity(Question question) {
        return new QuestionResponseDto(
                question.getQuestionId(),
                question.getText(),
                question.getSequence()
        );
    }
}