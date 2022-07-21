package com.Neoflex.deal.webclient;

import com.Neoflex.deal.exception.LoanOfferException;
import com.Neoflex.deal.model.LoanApplicationRequestDto;
import com.Neoflex.deal.model.LoanOfferDto;
import com.Neoflex.deal.model.ScoringDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConveyorRestTemplate {

    private final RestTemplate restTemplate;

    @Value("${url.conveyor.offers}")
    private String urlConveyorOffers;
    @Value("${url.conveyor.calculation}")
    private String urlConveyorCalculation;

    public List<LoanOfferDto> getOffers(LoanApplicationRequestDto loanApplicationRequestDto) {
        log.info("request to /conveyor/offers method start, request body: LoanApplicationRequestDto = {} ", loanApplicationRequestDto);
        ResponseEntity<List<LoanOfferDto>> responseEntityOffers = restTemplate.exchange(urlConveyorOffers,
                HttpMethod.POST, new HttpEntity<>(loanApplicationRequestDto), new ParameterizedTypeReference<>() {
                });
        List<LoanOfferDto> offers = responseEntityOffers.getBody();
        if (offers == null) {
            throw new LoanOfferException("Conveyor return null");
        }
        return offers;
    }

    public void calculate(ScoringDataDto scoringDataDTO) {
        log.info("request to /conveyor/calculation method start, request body: ScoringDataDto = {} ", scoringDataDTO);
        restTemplate.postForObject(urlConveyorCalculation, scoringDataDTO, Void.class);
    }

}
