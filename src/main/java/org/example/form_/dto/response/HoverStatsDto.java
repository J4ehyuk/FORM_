package org.example.form_.dto.response;

import java.util.List;

public record HoverStatsDto(
        Long questionId,
        List<OptionHoverStats> optionStats
) {}