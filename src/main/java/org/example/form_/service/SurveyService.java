package org.example.form_.service;

import lombok.RequiredArgsConstructor;
import org.example.form_.dto.SurveyResponseDto;
import org.example.form_.entity.Survey;
import org.example.form_.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    /**
     * 모든 설문 목록을 조회하여 SurveyResponseDto 리스트로 반환합니다.
     */
    public List<SurveyResponseDto> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        return surveys.stream()
                .map(SurveyResponseDto::fromEntity)
                .toList();
    }

    /**
     * 특정 ID의 설문을 조회하여 SurveyResponseDto로 반환합니다.
     * 존재하지 않는 경우 예외를 발생시킵니다.
     *
     * @param surveyId 조회할 설문 ID
     * @return SurveyResponseDto
     */
    public SurveyResponseDto getSurveyById(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("설문을 찾을 수 없습니다. ID = " + surveyId));
        return SurveyResponseDto.fromEntity(survey);
    }
}