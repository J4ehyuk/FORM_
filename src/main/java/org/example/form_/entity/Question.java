package org.example.form_.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Question { // 질문
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long questionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id", nullable = false)
  private Survey survey;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String text; // 질문

  @Column(nullable = false)
  private Long sequence; // 순서

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Choice> options; // 선택지 항목
}
