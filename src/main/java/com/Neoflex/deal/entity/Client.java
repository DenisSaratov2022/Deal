package com.Neoflex.deal.entity;

import com.Neoflex.deal.model.Gender;
import com.Neoflex.deal.model.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

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
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Application application;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport")
    private Passport passport;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employment")
    private Employment employment;
}
