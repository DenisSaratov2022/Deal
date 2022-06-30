package com.Neoflex.deal.service;
import com.Neoflex.deal.model.LoanApplicationRequestDTO;
import com.Neoflex.deal.model.LoanOfferDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DealService {

    @Value("${standard.rate}")
    private double rate;
    private static final Integer NUMBER_OF_ROUNDED_CHARACTERS = 8;
    private static final Integer NUMBER_OF_ROUNDED_CHARACTERS_FINAL = 2;
    private long applicationId = 0;

    public List<LoanOfferDTO> getOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
    return  null;
    }
    public void offerSelection(int id, LoanOfferDTO loanOfferDTO) {
    }
}

