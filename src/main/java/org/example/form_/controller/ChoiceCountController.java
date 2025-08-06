package org.example.form_.controller;

import lombok.RequiredArgsConstructor;
import org.example.form_.dto.request.ChoiceCountRequestDto;
import org.example.form_.dto.response.ChoiceCountResponseDto;
import org.example.form_.service.ChoiceCountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/choice-counts")
@RequiredArgsConstructor
public class ChoiceCountController {

    private final ChoiceCountService choiceCountService;

    // 선택지 카운트 증가
    @PostMapping("/increment")
    public ResponseEntity<Void> incrementChoiceCount(@RequestBody ChoiceCountRequestDto dto) {
        choiceCountService.incrementCount(dto.choiceId());
        return ResponseEntity.ok().build();
    }

    // 통계 조회 (기존 로직 유지 시)
    @GetMapping("/survey/{surveyId}")
    public List<ChoiceCountResponseDto> getStatsBySurvey(@PathVariable Long surveyId) {
        return choiceCountService.getStatsBySurveyId(surveyId);
    }
}