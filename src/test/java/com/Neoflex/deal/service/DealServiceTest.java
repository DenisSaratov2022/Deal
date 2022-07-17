package com.Neoflex.deal.service;

import com.Neoflex.deal.model.LoanApplicationRequestDTO;
import com.Neoflex.deal.model.LoanOfferDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DealServiceTest {

    @Autowired
    DealService dealService;

    @Test
    void getOffers_SuccessTest_withMinValues() {

        LoanApplicationRequestDTO loanApplicationRequestDTO = LoanApplicationRequestDTO.builder()
                .firstName("Darth")
                .lastName("Vader")
                .middleName("Lord")
                .amount(BigDecimal.valueOf(10000))
                .birthdate(LocalDate.now().minusYears(30))
                .passportNumber("111111")
                .passportSeries("2222")
                .term(6)
                .email("LordVader@gmail.com")
                .build();
        List<LoanOfferDTO> offers = dealService.getOffers(loanApplicationRequestDTO);
        for (var offer : offers) {
            assertNotNull(offer.getApplicationId());
            assertEquals(loanApplicationRequestDTO.getAmount(), offer.getRequestedAmount());
            assertEquals(loanApplicationRequestDTO.getTerm(), offer.getTerm());
            if (!offer.isInsuranceEnabled() && !offer.isSalaryClient()) {
                assertTrue(BigDecimal.valueOf(10000).subtract(offer.getTotalAmount()).abs().compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(1745).subtract(offer.getMonthlyPayment().abs()).compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(16).subtract(offer.getRate().abs()).compareTo(BigDecimal.ZERO.stripTrailingZeros()) <= 0);
            }
            if (offer.isInsuranceEnabled() && !offer.isSalaryClient()) {
                assertTrue(BigDecimal.valueOf(10100).subtract(offer.getTotalAmount()).abs().compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(1755).subtract(offer.getMonthlyPayment().abs()).compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(14.5).subtract(offer.getRate().abs()).compareTo(BigDecimal.ZERO.stripTrailingZeros()) <= 0);
            }
            if (offer.isSalaryClient() && !offer.isInsuranceEnabled()) {
                assertTrue(BigDecimal.valueOf(10000).subtract(offer.getTotalAmount()).abs().compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(1738).subtract(offer.getMonthlyPayment().abs()).compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(14.5).subtract(offer.getRate().abs()).compareTo(BigDecimal.ZERO.stripTrailingZeros()) <= 0);
            }
            if (offer.isSalaryClient() && offer.isInsuranceEnabled()) {
                assertTrue(BigDecimal.valueOf(10100).subtract(offer.getTotalAmount()).abs().compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(1748).subtract(offer.getMonthlyPayment().abs()).compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(13).subtract(offer.getRate().abs()).compareTo(BigDecimal.ZERO.stripTrailingZeros()) <= 0);
            }
                   assertTrue(offers.get(1).getRate().compareTo(offers.get(0).getRate()) < 0);
        }
    }
    @Test
    void getOffers_SuccessTest_withMaxValues() {
        LoanApplicationRequestDTO loanApplicationRequestDTO = LoanApplicationRequestDTO.builder()
                .firstName("Darth")
                .lastName("Vader")
                .middleName("Lord")
                .amount(BigDecimal.valueOf(10000000))
                .birthdate(LocalDate.now().minusYears(30))
                .passportNumber("111111")
                .passportSeries("2222")
                .term(60)
                .email("LordVader@gmail.com")
                .build();
        List<LoanOfferDTO> offers = dealService.getOffers(loanApplicationRequestDTO);
        assertEquals(4, offers.size());
        for (var offer : offers) {
            assertNotNull(offer.getApplicationId());
            assertEquals(loanApplicationRequestDTO.getAmount(), offer.getRequestedAmount());
            assertEquals(loanApplicationRequestDTO.getTerm(), offer.getTerm());
            if (!offer.isInsuranceEnabled() && !offer.isSalaryClient()) {
                assertTrue(BigDecimal.valueOf(10000000).subtract(offer.getTotalAmount()).abs().compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(243181).subtract(offer.getMonthlyPayment().abs()).compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(16).subtract(offer.getRate().abs()).compareTo(BigDecimal.valueOf(0)) <= 0);
            }
            if (offer.isInsuranceEnabled() && !offer.isSalaryClient()) {
                assertTrue(BigDecimal.valueOf(11000000).subtract(offer.getTotalAmount()).abs().compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(258811).subtract(offer.getMonthlyPayment().abs()).compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(14.5).subtract(offer.getRate().abs()).compareTo(BigDecimal.valueOf(0)) <= 0);
            }
            if (offer.isSalaryClient() && !offer.isInsuranceEnabled()) {
                assertTrue(BigDecimal.valueOf(10000000).subtract(offer.getTotalAmount()).abs().compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(235283).subtract(offer.getMonthlyPayment().abs()).compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(14.5).subtract(offer.getRate().abs()).compareTo(BigDecimal.valueOf(0)) <= 0);
            }
            if (offer.isSalaryClient() && offer.isInsuranceEnabled()) {
                assertTrue(BigDecimal.valueOf(11000000).subtract(offer.getTotalAmount()).abs().compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(250284).subtract(offer.getMonthlyPayment().abs()).compareTo(BigDecimal.valueOf(50)) <= 0);
                assertTrue(BigDecimal.valueOf(13).subtract(offer.getRate().abs()).compareTo(BigDecimal.valueOf(0)) <= 0);
            }
            assertTrue(offers.get(1).getRate().compareTo(offers.get(0).getRate()) < 0);
        }

    }

}
