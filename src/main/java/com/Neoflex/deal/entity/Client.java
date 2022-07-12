package com.Neoflex.deal.entity;

import com.Neoflex.deal.model.*;
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
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "application", nullable = false)
    @OneToOne(mappedBy ="client")
    private Application application;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport")
//    @OneToOne(mappedBy ="client", cascade = CascadeType.ALL)
    private Passport passport;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employment")
    private Employment employment;


}
