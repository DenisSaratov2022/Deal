package com.Neoflex.deal.model;
import com.Neoflex.deal.validation.ChekDateOfBirth;
import com.Neoflex.deal.validation.ChekPassportIssueDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoringDataDTO {

    @Min(10000)
    @Max(10000000)
    private BigDecimal amount;
    @Min(6)
    @Max(60)
    private Integer term;
    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;
    @Size(min = 2, max = 30)
    private String middleName;
    private Gender gender;
    @ChekDateOfBirth()
    private LocalDate birthdate;
    @NotBlank
    @Size(min = 4, max = 4)
    private String passportSeries;
    @NotBlank
    @Size(min = 6, max = 6)
    private String passportNumber;
    @ChekPassportIssueDate
    private LocalDate passportIssueDate;
    @NotBlank
    @Size(min = 5, max = 40)
    private String passportIssueBranch;
    private MaritalStatus maritalStatus;
    @Min(0)
    private Integer dependentAmount;
    @Valid
    private EmploymentDTO employment;
    @NotBlank
    @Size(min = 2, max = 30)
    private String account;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;

}
