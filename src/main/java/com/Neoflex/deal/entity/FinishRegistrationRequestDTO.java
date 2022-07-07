package com.Neoflex.deal.entity;

import com.Neoflex.deal.model.Gender;
import com.Neoflex.deal.model.MaritalStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class FinishRegistrationRequestDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Integer dependentAmount;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;
    @OneToOne(mappedBy = "finishRegistrationRequestDTO")
    private EmploymentDTO employment;
    private String account;
}
