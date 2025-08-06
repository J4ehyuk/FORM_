package org.example.form_.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Survey { // 설문 목록

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long surveyId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String subTitle;

  @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Question> questions; // 질문 목록
}
