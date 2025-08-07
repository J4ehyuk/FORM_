package org.example.form_.dto.response;

public record OptionHoverStats(
        String optionId,
        Long min,
        Long max,
        Double average,
        Double trimmedAverage
) {}
