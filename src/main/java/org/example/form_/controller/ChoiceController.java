package org.example.form_.controller;

import lombok.RequiredArgsConstructor;
import org.example.form_.dto.response.ChoiceResponseDto;
import org.example.form_.entity.Choice;
import org.example.form_.service.ChoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/choices")
@RequiredArgsConstructor
public class ChoiceController {

    private final ChoiceService choiceService;

    // 특정 질문에 속한 선택지 목록 조회
    @GetMapping("/question/{questionId}")
    public List<ChoiceResponseDto> getChoicesByQuestionId(@PathVariable Long questionId) {
        return choiceService.getChoicesByQuestionId(questionId)
                .stream()
                .map(ChoiceResponseDto::fromEntity)
                .toList();
    }
}