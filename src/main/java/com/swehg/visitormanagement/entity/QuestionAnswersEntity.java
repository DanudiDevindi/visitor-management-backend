package com.swehg.visitormanagement.entity;

import com.swehg.visitormanagement.enums.AnswerStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pass_card")
public class QuestionAnswersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionsEntity questionsEntity;
    @Enumerated(EnumType.STRING)
    private AnswerStatus answer;
    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private VisitorEntity visitorEntity;
}
