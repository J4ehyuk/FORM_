package org.example.form_.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Choice { // 질문 선택지

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  Long choiceId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id", nullable = false)
  private Question question;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String text; // 선택지 내용

  @Column(nullable = false)
  private Long sequence; // 순서
}
