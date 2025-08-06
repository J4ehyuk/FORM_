package org.example.form_.service;

import lombok.RequiredArgsConstructor;
import org.example.form_.dto.response.ChoiceCountResponseDto;
import org.example.form_.entity.Choice;
import org.example.form_.entity.ChoiceCount;
import org.example.form_.repository.ChoiceCountRepository;
import org.example.form_.repository.ChoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoiceCountService {

    private final ChoiceCountRepository choiceCountRepository;
    private final ChoiceRepository choiceRepository;

    /**
     * 선택지 응답 카운트를 1 증가시킵니다.
     * 만약 해당 선택지의 카운트가 없다면 새로 생성합니다.
     */
    @Transactional
    public void incrementCount(Long choiceId) {
        Choice choice = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 choiceId: " + choiceId));

        ChoiceCount choiceCount = choiceCountRepository.findByChoice(choice)
                .orElseGet(() -> {
                    ChoiceCount newCount = new ChoiceCount(choice, 0L);
                    return choiceCountRepository.save(newCount);
                });

        choiceCount.increment(); // count += 1
    }

    /**
     * 특정 설문에 대한 선택지 응답 통계를 조회합니다.
     */
    @Transactional(readOnly = true)
    public List<ChoiceCountResponseDto> getStatsBySurveyId(Long surveyId) {
        return choiceCountRepository.findStatsBySurveyId(surveyId);
    }
}