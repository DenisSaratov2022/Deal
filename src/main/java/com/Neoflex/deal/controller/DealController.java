package com.Neoflex.deal.controller;

import com.Neoflex.deal.model.FinishRegistrationRequestDto;
import com.Neoflex.deal.model.LoanApplicationRequestDto;
import com.Neoflex.deal.model.LoanOfferDto;
import com.Neoflex.deal.service.DealService;
import io.swagger.v3.oas.annotations.Operation;
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
//@Api
public class DealController {
    private final DealService dealService;


    @Operation(summary = "calculation Of PossibleConditions")
    @PostMapping("/deal/application")
    public ResponseEntity<List<LoanOfferDto>> dealApplication(@RequestBody @Valid LoanApplicationRequestDto loanApplicationRequestDto) {
        log.info("dealApplication method start: request body={}", loanApplicationRequestDto);
        return ResponseEntity.ok(dealService.getOffers(loanApplicationRequestDto));
    }

    @Operation(summary = "selecting one of the offers")
    @PutMapping("/deal/offer")
    public void dealOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        log.info("dealOffer method start: request body={}", loanOfferDto);
        dealService.offerSelection(loanOfferDto);
    }

    @Operation(summary = "completion of registration and full credit calculation")
    @PutMapping("/deal/calculate/{applicationId}")
    public void registrationFinish(@RequestBody @Valid FinishRegistrationRequestDto finishRegistrationRequestDto, Long applicationId) {
        log.info("dealCalculate method start: request body={}, params = {}", finishRegistrationRequestDto, applicationId);
        dealService.completionOfRegistration(finishRegistrationRequestDto, applicationId);
    }
}
