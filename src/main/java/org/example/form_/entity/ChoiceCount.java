package org.example.form_.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChoiceCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "choice_id")
    private Choice choice;

    @Column(nullable = false)
    private Long count;

    public ChoiceCount(Choice choice, Long count) {
        this.choice = choice;
        this.count = count;
    }

    public void increment() {
        this.count += 1;
    }
}