package com.Neoflex.deal.mapping;

import com.Neoflex.deal.entity.Client;
import com.Neoflex.deal.entity.Passport;
import com.Neoflex.deal.model.LoanApplicationRequestDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InitClientMapper {
    public Client initClient (LoanApplicationRequestDto loanApplicationRequestDto){
        Passport passport = Passport.builder()
                .passportNumber(loanApplicationRequestDto.getPassportNumber())
                .passportSeries(loanApplicationRequestDto.getPassportSeries())
                .build();
        Client client = Client.builder()
                .lastName(loanApplicationRequestDto.getLastName())
                .firstName(loanApplicationRequestDto.getFirstName())
                .middleNme(loanApplicationRequestDto.getMiddleName())
                .birthDate(loanApplicationRequestDto.getBirthdate())
                .email(loanApplicationRequestDto.getEmail())
                .passport(passport)
                .build();
        return  client;
    }
}
