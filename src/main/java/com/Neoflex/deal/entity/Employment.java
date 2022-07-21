package com.Neoflex.deal.entity;

import com.Neoflex.deal.model.EmploymentStatus;
import com.Neoflex.deal.model.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Position position;
    private String account;
    @OneToOne(mappedBy = "employment", cascade = CascadeType.ALL)
    private Client client;
}
