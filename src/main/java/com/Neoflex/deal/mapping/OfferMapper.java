package com.Neoflex.deal.mapping;

import com.Neoflex.deal.entity.Application;
import com.Neoflex.deal.entity.ApplicationStatusHistory;
import com.Neoflex.deal.entity.LoanOffer;
import com.Neoflex.deal.model.LoanOfferDto;
import com.Neoflex.deal.model.Status;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class OfferMapper {
    public static Application offerSelectionToApplicationMapping (Application application, LoanOfferDto loanOfferDto) {
        ApplicationStatusHistory applicationStatusHistory = ApplicationStatusHistory.builder()
                .status(Status.PREAPPROVAL)
                .time(LocalDateTime.now())
                .build();
        List<ApplicationStatusHistory> applicationStatusHistories = application.getApplicationStatusHistories();
        applicationStatusHistories.add(applicationStatusHistory);
        LoanOffer loanOffer = LoanOffer.builder()
                .applicationId(loanOfferDto.getApplicationId())
                .requestedAmount(loanOfferDto.getRequestedAmount())
                .totalAmount(loanOfferDto.getTotalAmount())
                .term(loanOfferDto.getTerm())
                .monthlyPayment(loanOfferDto.getMonthlyPayment())
                .rate(loanOfferDto.getRate())
                .isSalaryClient(loanOfferDto.isSalaryClient())
                .isInsuranceEnabled(loanOfferDto.isInsuranceEnabled())
                .build();
        application.setStatus(applicationStatusHistory.getStatus());
        application.setApplicationStatusHistories(applicationStatusHistories);
        application.setAppliedOffer(loanOffer);
        return application;
    }
}
