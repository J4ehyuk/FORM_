package org.example.form_.controller;

import lombok.RequiredArgsConstructor;
import org.example.form_.dto.response.QuestionResponseDto;
import org.example.form_.entity.Question;
import org.example.form_.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 특정 설문에 속한 질문 목록 조회
    @GetMapping("/survey/{surveyId}")
    public List<QuestionResponseDto> getQuestionsBySurveyId(@PathVariable Long surveyId) {
        return questionService.getQuestionsBySurveyId(surveyId).stream()
                .map(QuestionResponseDto::fromEntity)
                .toList();
    }
}