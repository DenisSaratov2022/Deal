package com.Neoflex.deal.entity;

import com.Neoflex.deal.model.*;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String lastName;
    private String firstName;
    private String middleNme;
    private LocalDate birthDate;
    private String email;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Integer dependentAmount;
    @OneToOne
    @JoinColumn(name = "application", nullable = false)
    private Application application;
    @OneToOne
    @JoinColumn(name = "passport", nullable = false)
    private Passport passport;
    @OneToOne
    @JoinColumn(name = "employment", nullable = false)
    private Employment employment;


}
