package com.Neoflex.deal.mapping;

import com.Neoflex.deal.entity.Application;
import com.Neoflex.deal.entity.Employment;
import com.Neoflex.deal.model.FinishRegistrationRequestDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationMapper {
    public static void finishRegistrationToApplicationMapping(FinishRegistrationRequestDto finishRegistrationRequestDto, Application application) {
        Employment employment = Employment.builder()
                .salary(finishRegistrationRequestDto.getEmployment().getSalary())
                .employmentStatus(finishRegistrationRequestDto.getEmployment().getEmploymentStatus())
                .workExperienceCurrent(finishRegistrationRequestDto.getEmployment().getWorkExperienceCurrent())
                .account(finishRegistrationRequestDto.getAccount())
                .workExperienceTotal(finishRegistrationRequestDto.getEmployment().getWorkExperienceTotal())
                .position(finishRegistrationRequestDto.getEmployment().getPosition())
                .build();
        application.getClient().getPassport().setPassportIssueDate(finishRegistrationRequestDto.getPassportIssueDate());
        application.getClient().getPassport().setPassportIssueBranch(finishRegistrationRequestDto.getPassportIssueBranch());
        application.getClient().setMaritalStatus(finishRegistrationRequestDto.getMaritalStatus());
        application.getClient().setDependentAmount(finishRegistrationRequestDto.getDependentAmount());
        application.getClient().setGender(finishRegistrationRequestDto.getGender());
        application.getClient().setEmployment(employment);
    }
}
