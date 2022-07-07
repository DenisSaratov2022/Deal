package com.Neoflex.deal.entity;

import com.Neoflex.deal.model.EmploymentStatus;
import com.Neoflex.deal.model.Position;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
public class EmploymentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private EmploymentStatus employmentStatus;
    @NotBlank
    @Size(min = 12, max = 12)
    private String employerINN;
    @Min(10000)
    private BigDecimal salary;
    private Position position;
    @Min(0)
    private Integer workExperienceTotal;
    @Min(0)
    private Integer workExperienceCurrent;
    @OneToOne
    @JoinColumn(name = "finishRegistrationRequestDTO", nullable = false)
    private FinishRegistrationRequestDTO finishRegistrationRequestDTO;

}
