package org.example.form_.dto;

public record SankeyLinkDto(
        String from,
        String to,
        int size
) {}