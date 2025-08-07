package org.example.form_.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClickPerParticipantDto {
    private String optionId;
    private Long clickCount;
    private Long participantCount;
    private Double clickPerParticipant;
}
