package com.Neoflex.deal.service;

import com.Neoflex.deal.entity.*;
import com.Neoflex.deal.exception.LoanOfferException;
import com.Neoflex.deal.model.*;
import com.Neoflex.deal.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealService {
    private final ApplicationRepository applicationRepository;
    private final RestTemplate restTemplate;
    @Value("${url.conveyor.offers}")
    private String urlConveyorOffers;
    @Value("${url.conveyor.calculation}")
    private String urlConveyorCalculation;

    public List<LoanOfferDTO> getOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        Passport passport = Passport.builder()
                .passportNumber(loanApplicationRequestDTO.getPassportNumber())
                .passportSeries(loanApplicationRequestDTO.getPassportSeries())
                .build();
        Client client = Client.builder()
                .lastName(loanApplicationRequestDTO.getLastName())
                .firstName(loanApplicationRequestDTO.getFirstName())
                .middleNme(loanApplicationRequestDTO.getMiddleName())
                .birthDate(loanApplicationRequestDTO.getBirthdate())
                .email(loanApplicationRequestDTO.getEmail())
                .passport(passport)
                .build();
        Application application =
                Application.builder()
                        .client(client)
                        .creationDate(LocalDate.now())
                        .build();
        applicationRepository.save(application);
        LoanOfferDTO[] array = restTemplate.postForObject(urlConveyorOffers, loanApplicationRequestDTO, LoanOfferDTO[].class);
        if (array == null) {
            throw new LoanOfferException("Conveyor return null");
        }
        for (LoanOfferDTO loanOfferDTO : array) {
            loanOfferDTO.setApplicationId(application.getId());
        }
        log.info("getOffers method return: {}", Arrays.asList(array));
        return Arrays.asList(array);
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
        application.setStatus(applicationStatusHistory.getStatus());
        application.setApplicationStatusHistories(applicationStatusHistories);
        application.setAppliedOffer(loanOffer);
        applicationRepository.save(application);
        log.info("Method offerSelection init appliedOffer, save application");
    }

    public void completionOfRegistration(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow();
        Employment employment = Employment.builder()
                .salary(finishRegistrationRequestDTO.getEmployment().getSalary())
                .employmentStatus(finishRegistrationRequestDTO.getEmployment().getEmploymentStatus())
                .workExperienceCurrent(finishRegistrationRequestDTO.getEmployment().getWorkExperienceCurrent())
                .account(finishRegistrationRequestDTO.getAccount())
                .workExperienceTotal(finishRegistrationRequestDTO.getEmployment().getWorkExperienceTotal())
                .position(finishRegistrationRequestDTO.getEmployment().getPosition())
                .build();
        application.getClient().getPassport().setPassportIssueDate(finishRegistrationRequestDTO.getPassportIssueDate());
        application.getClient().getPassport().setPassportIssueBranch(finishRegistrationRequestDTO.getPassportIssueBranch());
        application.getClient().setMaritalStatus(finishRegistrationRequestDTO.getMaritalStatus());
        application.getClient().setDependentAmount(finishRegistrationRequestDTO.getDependentAmount());
        application.getClient().setGender(finishRegistrationRequestDTO.getGender());
        application.getClient().setEmployment(employment);
        applicationRepository.save(application);
        EmploymentDTO employmentDTO = EmploymentDTO.builder()
                .employmentStatus(application.getClient().getEmployment().getEmploymentStatus())
                .salary(application.getClient().getEmployment().getSalary())
                .position(application.getClient().getEmployment().getPosition())
                .employerINN(finishRegistrationRequestDTO.getEmployment().getEmployerINN())
                .workExperienceCurrent(application.getClient().getEmployment().getWorkExperienceCurrent())
                .workExperienceTotal(application.getClient().getEmployment().getWorkExperienceTotal())
                .build();
        ScoringDataDTO scoringDataDTO = ScoringDataDTO.builder()
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
        restTemplate.postForObject( urlConveyorCalculation, scoringDataDTO, Void.class);
        log.info("completionOfRegistration method executed the post request to the microservice conveyor");
    }
}

