package org.example.form_.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClickPerParticipantDto {
    private Long questionId;
    private Long clickCount;
    private Long participantCount;
    private Double clickPerParticipant;
}
