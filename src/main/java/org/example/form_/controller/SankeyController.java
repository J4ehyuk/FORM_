package org.example.form_.controller;

import lombok.RequiredArgsConstructor;
import org.example.form_.dto.SankeyLinkDto;
import org.example.form_.service.SankeyDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sankey")
@RequiredArgsConstructor
public class SankeyController {

    private final SankeyDataService sankeyDataService;

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<SankeyLinkDto>> getSankeyData(@PathVariable Long questionId) {
        return ResponseEntity.ok(sankeyDataService.getSankeyLinksByQuestion(questionId));
    }
}