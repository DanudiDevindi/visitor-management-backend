package com.swehg.visitormanagement.entity;

import com.swehg.visitormanagement.enums.PassCardStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pass_card")
public class PassCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private PassCardStatus status;
}
