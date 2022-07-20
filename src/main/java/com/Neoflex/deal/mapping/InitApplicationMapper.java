package com.Neoflex.deal.mapping;

import com.Neoflex.deal.entity.Application;
import com.Neoflex.deal.entity.Client;
import com.Neoflex.deal.model.LoanApplicationRequestDto;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class InitApplicationMapper {
    public Application initApplication (Client client) {
        Application application =
                Application.builder()
                        .client(client)
                        .creationDate(LocalDate.now())
                        .build();
        return application;
    }
}
