package com.Neoflex.deal.model;

import com.Neoflex.deal.validation.ChekPassportIssueDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinishRegistrationRequestDto {

    private Gender gender;
    private MaritalStatus maritalStatus;
    @Min(0)
    private Integer dependentAmount;
    @ChekPassportIssueDate
    private LocalDate passportIssueDate;
    @NotBlank
    @Size(min = 5, max = 40)
    private String passportIssueBranch;
    private EmploymentDto employment;
    @NotBlank
    @Size(min = 2, max = 30)
    private String account;
}
