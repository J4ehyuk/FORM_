package org.example.form_.dto.response;

import lombok.Builder;

@Builder
public record IdlePeriodStatsDto(
        Long questionId,
        Long count,
        Long minDuration,
        Long maxDuration,
        Double avgDuration,
        Double trimmedAverageDuration
) {}