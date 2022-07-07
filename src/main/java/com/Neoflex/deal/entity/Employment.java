package com.Neoflex.deal.entity;

import com.Neoflex.deal.model.EmploymentStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private EmploymentStatus employmentStatus;
    private String employer;
    private BigDecimal salary;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
    private String account;
    @OneToOne(mappedBy = "employment")
    private Client client;
}
