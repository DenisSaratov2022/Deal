package com.Neoflex.deal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentDto {

    private EmploymentStatus employmentStatus;
    @Size(min = 12, max = 12)
    private String employerINN;
    @Min(10000)
    private BigDecimal salary;
    private Position position;
    @Min(0)
    private Integer workExperienceTotal;
    @Min(0)
    private Integer workExperienceCurrent;
}
