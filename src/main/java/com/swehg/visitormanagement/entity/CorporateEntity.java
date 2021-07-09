package com.swehg.visitormanagement.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "corporate")
public class CorporateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column(length = 300)
    private String address;
    @Column
    private String tel;
    @Column
    private String email;
}
