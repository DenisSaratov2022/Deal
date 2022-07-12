package com.Neoflex.deal.controller;

import com.Neoflex.deal.model.FinishRegistrationRequestDTO;
import com.Neoflex.deal.model.LoanApplicationRequestDTO;
import com.Neoflex.deal.model.LoanOfferDTO;
import com.Neoflex.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class DealController {
    private final DealService dealService;

    @PostMapping("/deal/application")
    public ResponseEntity<List<LoanOfferDTO>> dealApplication(@RequestBody @Valid LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("dealApplication method started with params: " + loanApplicationRequestDTO.toString());
        return dealService.getOffers(loanApplicationRequestDTO);
    }

    @PutMapping("/deal/offer")
    public void dealOffer(@RequestBody  LoanOfferDTO loanOfferDTO) {
        log.info("dealOffer method started with params: " + loanOfferDTO.toString());
        dealService.offerSelection(loanOfferDTO);
    }

    @PutMapping(" /deal/calculate/{applicationId}")
    public void registrationFinish(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId) {
        log.info("dealOffer method started with params: " + finishRegistrationRequestDTO.toString());
        dealService.completionOfRegistration(finishRegistrationRequestDTO, applicationId);
    }

}
