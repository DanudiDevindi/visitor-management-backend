package com.swehg.visitormanagement.entity;

import com.swehg.visitormanagement.enums.QuestionStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class QuestionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String question;
    @ManyToOne
    @JoinColumn(name = "question_category_id")
    private QuestionCategoryEntity category;
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;
}
