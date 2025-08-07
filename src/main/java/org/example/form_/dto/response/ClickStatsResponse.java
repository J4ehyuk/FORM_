package org.example.form_.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ClickStatsResponse {
    private Long questionId;
    private List<ClickPerParticipantDto> optionStats;
}
