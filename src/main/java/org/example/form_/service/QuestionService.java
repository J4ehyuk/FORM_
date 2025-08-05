package org.example.form_.service;

import lombok.RequiredArgsConstructor;
import org.example.form_.entity.Question;
import org.example.form_.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getQuestionsBySurveyId(Long surveyId) {
        return questionRepository.findBySurvey_SurveyId(surveyId);
    }
}