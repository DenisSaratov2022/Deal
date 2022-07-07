package com.Neoflex.deal.service;

import com.Neoflex.deal.entity.Application;
import com.Neoflex.deal.entity.Client;
import com.Neoflex.deal.entity.FinishRegistrationRequestDTO;
import com.Neoflex.deal.model.LoanApplicationRequestDTO;
import com.Neoflex.deal.model.LoanOfferDTO;
import com.Neoflex.deal.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        LoanOfferDTO [] array = restTemplate.postForObject("http://localhost:8080/conveyor/offers",loanApplicationRequestDTO, LoanOfferDTO[].class);
        for (int i = 0; i < (array != null ? array.length : 0); i++) {
            array[i].setApplicationId(application.getId());
        }
        assert array != null;
        return  ResponseEntity.ok(Arrays.asList(array));
    }
    public void offerSelection(LoanOfferDTO loanOfferDTO) {
    }

    public  void completionOfRegistration (FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId ) {

    }
}

