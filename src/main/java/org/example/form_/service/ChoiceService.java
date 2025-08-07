package org.example.form_.service;

import lombok.RequiredArgsConstructor;
import org.example.form_.entity.Choice;
import org.example.form_.repository.ChoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoiceService {

    private final ChoiceRepository choiceRepository;
    
    public List<Choice> getChoicesByQuestionId(Long questionId) {
        return choiceRepository.findByQuestion_QuestionIdOrderBySequence(questionId);
    }
}