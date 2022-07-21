package com.Neoflex.deal.service;

import com.Neoflex.deal.entity.Application;
import com.Neoflex.deal.entity.Client;
import com.Neoflex.deal.mapping.*;
import com.Neoflex.deal.model.FinishRegistrationRequestDto;
import com.Neoflex.deal.model.LoanApplicationRequestDto;
import com.Neoflex.deal.model.LoanOfferDto;
import com.Neoflex.deal.model.ScoringDataDto;
import com.Neoflex.deal.repository.ApplicationRepository;
import com.Neoflex.deal.repository.ClientRepository;
import com.Neoflex.deal.webclient.ConveyorRestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealService {
    private final ApplicationRepository applicationRepository;
    private final ClientRepository clientRepository;
    private final ConveyorRestTemplate conveyorRestTemplate;

    public List<LoanOfferDto> getOffers(LoanApplicationRequestDto loanApplicationRequestDto) {

        Client client = InitClientMapper.initClient(loanApplicationRequestDto);
        clientRepository.save(client);
        Application application = InitApplicationMapper.initApplication(client);
        application = applicationRepository.save(application);
        List<LoanOfferDto> offers = conveyorRestTemplate.getOffers(loanApplicationRequestDto);
        for (LoanOfferDto loanOfferDTO : offers) {
            loanOfferDTO.setApplicationId(application.getId());
        }
        log.info("getOffers method return: {}", offers);
        return offers;
    }

    public void offerSelection(LoanOfferDto loanOfferDto) {
        Application application = applicationRepository.findById(loanOfferDto.getApplicationId()).orElseThrow();
        applicationRepository.save(OfferMapper.offerSelectionToApplicationMapping(application, loanOfferDto));
        log.info("Method offerSelection init appliedOffer, save application");
    }

    public void completionOfRegistration(FinishRegistrationRequestDto finishRegistrationRequestDto, Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow();
        ApplicationMapper.finishRegistrationToApplicationMapping(finishRegistrationRequestDto, application);
        applicationRepository.save(application);
        ScoringDataDto scoringDataDto = ConveyorMapper.completionOfRegistrationMapping(finishRegistrationRequestDto, application);
        conveyorRestTemplate.calculate(scoringDataDto);
        log.info("completionOfRegistration method executed the post request to the microservice conveyor");
    }
}

