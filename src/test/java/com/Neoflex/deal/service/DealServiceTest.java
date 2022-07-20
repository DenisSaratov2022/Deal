package com.Neoflex.deal.service;

import com.Neoflex.deal.entity.Application;
import com.Neoflex.deal.entity.Client;
import com.Neoflex.deal.model.LoanApplicationRequestDto;
import com.Neoflex.deal.model.LoanOfferDto;
import com.Neoflex.deal.repository.ApplicationRepository;
import com.Neoflex.deal.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class DealServiceTest {

    @Autowired
    DealService dealService;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private ApplicationRepository applicationRepository;
    @MockBean
    private ClientRepository clientRepository;

    @Test
    void getOffers_SuccessTest_withMinValues() {
        when(applicationRepository.save(Mockito.any(Application.class)))
                .thenReturn(Application.builder()
                        .id(2L)
                        .build());
        when(clientRepository.save(Mockito.any(Client.class)))
                .thenReturn(Client.builder()
                        .id(2L)
                        .build());
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<LoanOfferDto>>>any()))
                .thenReturn(ResponseEntity.ok(List.of(getLoanOfferDto2(), getLoanOfferDto())));
        LoanApplicationRequestDto loanApplicationRequestDTO = getLoanApplicationRequestDto(10000, 6);
        List<LoanOfferDto> offers = dealService.getOffers(loanApplicationRequestDTO);
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

    private LoanOfferDto getLoanOfferDto() {
        return LoanOfferDto.builder()
                .applicationId(1L)
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .monthlyPayment(BigDecimal.valueOf(5000))
                .rate(BigDecimal.valueOf(14.5))
                .requestedAmount(BigDecimal.valueOf(10000))
                .totalAmount(BigDecimal.valueOf(10100))
                .term(6)
                .build();
    }

    private LoanOfferDto getLoanOfferDto2() {
        return LoanOfferDto.builder()
                .applicationId(1L)
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .monthlyPayment(BigDecimal.valueOf(1745))
                .rate(BigDecimal.valueOf(16))
                .requestedAmount(BigDecimal.valueOf(10000))
                .totalAmount(BigDecimal.valueOf(10000))
                .term(6)
                .build();
    }


    @Test
    void getOffers_SuccessTest_withMaxValues() {
        when(applicationRepository.save(Mockito.any(Application.class)))
                .thenReturn(Application.builder()
                        .id(3L)
                        .build());
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<LoanOfferDto>>>any()))
                .thenReturn(ResponseEntity.ok(List.of(getLoanOfferDto4(), getLoanOfferDto3())));
        LoanApplicationRequestDto loanApplicationRequestDTO = getLoanApplicationRequestDto(10000000, 60);
        List<LoanOfferDto> offers = dealService.getOffers(loanApplicationRequestDTO);
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
    private LoanOfferDto getLoanOfferDto3() {
        return LoanOfferDto.builder()
                .applicationId(1L)
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .monthlyPayment(BigDecimal.valueOf(250284))
                .rate(BigDecimal.valueOf(13))
                .requestedAmount(BigDecimal.valueOf(10000000))
                .totalAmount(BigDecimal.valueOf(11000000))
                .term(60)
                .build();
    }

    private LoanOfferDto getLoanOfferDto4() {
        return LoanOfferDto.builder()
                .applicationId(2L)
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .monthlyPayment(BigDecimal.valueOf(258811))
                .rate(BigDecimal.valueOf(14.5))
                .requestedAmount(BigDecimal.valueOf(10000000))
                .totalAmount(BigDecimal.valueOf(11000000))
                .term(60)
                .build();
    }

    private LoanApplicationRequestDto getLoanApplicationRequestDto(int i, int i2) {
        return LoanApplicationRequestDto.builder()
                .firstName("Darth")
                .lastName("Vader")
                .middleName("Lord")
                .amount(BigDecimal.valueOf(i))
                .birthdate(LocalDate.now().minusYears(30))
                .term(i2)
                .passportNumber("111111")
                .passportSeries("2222")
                .email("LordVader@gmail.com")
                .build();
    }

}
