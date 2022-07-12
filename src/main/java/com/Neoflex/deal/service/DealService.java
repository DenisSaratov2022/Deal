package com.Neoflex.deal.service;

import com.Neoflex.deal.entity.*;
import com.Neoflex.deal.exception.LoanOfferException;
import com.Neoflex.deal.model.*;
import com.Neoflex.deal.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealService {
    private final ApplicationRepository applicationRepository;
    private final RestTemplate restTemplate;

    public ResponseEntity<List<LoanOfferDTO>> getOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        Client client = Client.builder()
                .lastName(loanApplicationRequestDTO.getLastName())
                .firstName(loanApplicationRequestDTO.getFirstName())
                .middleNme(loanApplicationRequestDTO.getMiddleName())
                .birthDate(loanApplicationRequestDTO.getBirthdate())
                .email(loanApplicationRequestDTO.getEmail())
                .build();
        Application application =
                Application.builder()
                        .client(client)
                        .build();
        applicationRepository.save(application);
        LoanOfferDTO[] array = restTemplate.postForObject("http://localhost:8082/conveyor/offers", loanApplicationRequestDTO, LoanOfferDTO[].class);
        if (array == null) {
            throw new LoanOfferException("Conveyor return null");
        }
        for (LoanOfferDTO loanOfferDTO : array) {
            loanOfferDTO.setApplicationId(application.getId());
        }
        return ResponseEntity.ok(Arrays.asList(array));
    }

    public void offerSelection(LoanOfferDTO loanOfferDTO) {

        Application application = applicationRepository.findById(loanOfferDTO.getApplicationId()).orElseThrow();
        ApplicationStatusHistory applicationStatusHistory = ApplicationStatusHistory.builder()
                .status(Status.PREAPPROVAL)
                .time(LocalDateTime.now())
                .build();
        List<ApplicationStatusHistory> applicationStatusHistories = application.getApplicationStatusHistories();
        applicationStatusHistories.add(applicationStatusHistory);
        LoanOffer loanOffer = LoanOffer.builder()
                .applicationId(loanOfferDTO.getApplicationId())
                .requestedAmount(loanOfferDTO.getRequestedAmount())
                .totalAmount(loanOfferDTO.getTotalAmount())
                .term(loanOfferDTO.getTerm())
                .monthlyPayment(loanOfferDTO.getMonthlyPayment())
                .rate(loanOfferDTO.getRate())
                .isSalaryClient(loanOfferDTO.isSalaryClient())
                .isInsuranceEnabled(loanOfferDTO.isInsuranceEnabled())
                .build();
        Application.builder()
                .status(applicationStatusHistory.getStatus())
                .applicationStatusHistories(applicationStatusHistories)
                .appliedOffer(loanOffer)
                .build();
        applicationRepository.save(application);
    }

    public void completionOfRegistration(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow();
        EmploymentDTO employment = EmploymentDTO.builder()
                .employmentStatus(application.getClient().getEmployment().getEmploymentStatus())
                .salary(application.getClient().getEmployment().getSalary())
                .position(finishRegistrationRequestDTO.getEmployment().getPosition())
                .employerINN(finishRegistrationRequestDTO.getEmployment().getEmployerINN())
                .workExperienceCurrent(finishRegistrationRequestDTO.getEmployment().getWorkExperienceCurrent())
                .workExperienceTotal(finishRegistrationRequestDTO.getEmployment().getWorkExperienceTotal())
                .build();
        ScoringDataDTO scoringDataDTO = ScoringDataDTO.builder()
                .amount(BigDecimal.valueOf(finishRegistrationRequestDTO.getDependentAmount()))
                .lastName(application.getClient().getLastName())
                .firstName(application.getClient().getFirstName())
                .middleName(application.getClient().getMiddleNme())
                .gender(finishRegistrationRequestDTO.getGender())
                .birthdate(application.getClient().getBirthDate())
                .passportSeries(application.getClient().getPassport().getPassportSeries())
                .passportNumber(application.getClient().getPassport().getPassportNumber())
                .passportIssueBranch(application.getClient().getPassport().getPassportIssueBranch())
                .passportIssueDate(application.getClient().getPassport().getPassportIssueDate())
                .maritalStatus(application.getClient().getMaritalStatus())
                .dependentAmount(application.getClient().getDependentAmount())
                .employment(employment)
                .account(finishRegistrationRequestDTO.getAccount())
             //   .isInsuranceEnabled()
               /// .term()
              //  .isSalaryClient()
                .build();
    }
}

