package com.Neoflex.deal.mapping;

import com.Neoflex.deal.entity.Application;
import com.Neoflex.deal.model.EmploymentDto;
import com.Neoflex.deal.model.FinishRegistrationRequestDto;
import com.Neoflex.deal.model.ScoringDataDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConveyorMapper {
    public static ScoringDataDto completionOfRegistrationMapping(FinishRegistrationRequestDto finishRegistrationRequestDto, Application application) {
        EmploymentDto employmentDTO = EmploymentDto.builder()
                .employmentStatus(application.getClient().getEmployment().getEmploymentStatus())
                .salary(application.getClient().getEmployment().getSalary())
                .position(application.getClient().getEmployment().getPosition())
                .employerINN(finishRegistrationRequestDto.getEmployment().getEmployerINN())
                .workExperienceCurrent(application.getClient().getEmployment().getWorkExperienceCurrent())
                .workExperienceTotal(application.getClient().getEmployment().getWorkExperienceTotal())
                .build();
        return ScoringDataDto.builder()
                .amount(application.getAppliedOffer().getRequestedAmount())
                .lastName(application.getClient().getLastName())
                .firstName(application.getClient().getFirstName())
                .middleName(application.getClient().getMiddleNme())
                .gender(application.getClient().getGender())
                .birthdate(application.getClient().getBirthDate())
                .passportSeries(application.getClient().getPassport().getPassportSeries())
                .passportNumber(application.getClient().getPassport().getPassportNumber())
                .passportIssueBranch(application.getClient().getPassport().getPassportIssueBranch())
                .passportIssueDate(application.getClient().getPassport().getPassportIssueDate())
                .maritalStatus(application.getClient().getMaritalStatus())
                .dependentAmount(application.getClient().getDependentAmount())
                .employment(employmentDTO)
                .account(application.getClient().getEmployment().getAccount())
                .isInsuranceEnabled(application.getAppliedOffer().isInsuranceEnabled())
                .term(application.getAppliedOffer().getTerm())
                .isSalaryClient(application.getAppliedOffer().isSalaryClient())
                .build();
    }
}
