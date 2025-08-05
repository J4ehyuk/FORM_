package org.example.form_.controller;

import lombok.RequiredArgsConstructor;
import org.example.form_.dto.SurveyResponseDto;
import org.example.form_.service.SurveyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    /**
     * 전체 설문 목록을 조회합니다.
     * - 제목, 질문 및 선택지 정보를 포함한 전체 설문 응답을 반환합니다.
     *
     * @return 전체 설문 응답 리스트
     */
    @GetMapping
    public List<SurveyResponseDto> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    /**
     * 특정 설문 ID에 해당하는 설문 상세 정보를 조회합니다.
     * - 설문 제목, 포함된 질문, 선택지를 포함하여 반환합니다.
     *
     * @param surveyId 조회할 설문 ID
     * @return 설문 상세 응답 DTO
     */
    @GetMapping("/{surveyId}")
    public SurveyResponseDto getSurvey(@PathVariable Long surveyId) {
        return surveyService.getSurveyById(surveyId);
    }
}