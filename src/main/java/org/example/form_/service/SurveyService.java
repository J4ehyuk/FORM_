package org.example.form_.service;

import lombok.RequiredArgsConstructor;
import org.example.form_.dto.request.SurveyRequestDto;
import org.example.form_.dto.response.SurveyResponseDto;
import org.example.form_.entity.Choice;
import org.example.form_.entity.Question;
import org.example.form_.entity.Survey;
import org.example.form_.repository.SurveyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public SurveyResponseDto createSurvey(SurveyRequestDto dto) {
        // 설문 엔티티 생성
        Survey survey = Survey.builder()
                .title(dto.title())
                .description(dto.description())
                .build();

        // 질문 및 선택지 추가
        List<Question> questions = dto.questions().stream()
                .map(qDto -> {
                    Question question = Question.builder()
                            .survey(survey)
                            .text(qDto.text())
                            .sequence(qDto.sequence())
                            .build();

                    List<Choice> choices = qDto.options().stream()
                            .map(cDto -> Choice.builder()
                                    .question(question)
                                    .text(cDto.text())
                                    .sequence(cDto.sequence())
                                    .build())
                            .toList();

                    question.setOptions(choices);
                    return question;
                }).toList();

        survey.setQuestions(questions);

        // 저장 및 DTO 반환
        Survey savedSurvey = surveyRepository.save(survey);
        return SurveyResponseDto.fromEntity(savedSurvey);
    }
}