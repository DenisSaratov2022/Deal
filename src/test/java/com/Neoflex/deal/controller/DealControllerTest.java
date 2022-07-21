package com.Neoflex.deal.controller;

import com.Neoflex.deal.entity.Application;
import com.Neoflex.deal.model.LoanApplicationRequestDto;
import com.Neoflex.deal.model.LoanOfferDto;
import com.Neoflex.deal.repository.ApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class DealControllerTest {

    public static final int MIN_AMOUNT = 100000;
    public static final int TERM = 6;
    public static final int MIN_TERM = 6;
    public static final String SMALL_PASSPORT_SERIES = "444";
    public static final int SMALL_AMOUNT = 1000;
    public static final String PASSPORT_SERIES = "4444";
    public static final int SMALL_TERM = 5;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private ApplicationRepository applicationRepository;

    private final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    @Test
    void controller_dealApplication_SuccessTest() throws Exception {
        when(applicationRepository.save(Mockito.any(Application.class)))
                .thenReturn(Application.builder()
                        .id(2L)
                        .build());
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<LoanOfferDto>>>any()))
                .thenReturn(ResponseEntity.ok(Collections.singletonList(LoanOfferDto.builder()
                        .applicationId(1L)
                        .isInsuranceEnabled(true)
                        .isSalaryClient(false)
                        .monthlyPayment(BigDecimal.valueOf(5000))
                        .rate(BigDecimal.valueOf(10))
                        .requestedAmount(BigDecimal.valueOf(100000))
                        .totalAmount(BigDecimal.valueOf(110000))
                        .build())));
        LoanApplicationRequestDto loanApplicationRequestDto = CreateLoanApplicationRequestDto(MIN_TERM, MIN_AMOUNT, PASSPORT_SERIES);
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void controller_dealOffer_SuccessTest() throws Exception {
        when(applicationRepository.findById(anyLong()))
                .thenReturn(Optional.of(
                        Application.builder()
                                .applicationStatusHistories(new ArrayList<>())
                                .build()));
        LoanOfferDto loanOfferDto = createLoanOfferDto();
        mockMvc.perform(put("/deal/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanOfferDto)))
                .andExpect(status().isOk());
    }

    @Test
    void controller_dealApplication_SmallTermTest() throws Exception {
        LoanApplicationRequestDto loanApplicationRequestDto = CreateLoanApplicationRequestDto(SMALL_TERM, MIN_AMOUNT, PASSPORT_SERIES);
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void controller_dealApplication_SmallAmountTest() throws Exception {
        LoanApplicationRequestDto loanApplicationRequestDto = CreateLoanApplicationRequestDto(TERM, SMALL_AMOUNT, PASSPORT_SERIES);
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void controller_dealApplication_SmallPassportSeries() throws Exception {
        LoanApplicationRequestDto loanApplicationRequestDto = CreateLoanApplicationRequestDto(MIN_TERM, MIN_AMOUNT, SMALL_PASSPORT_SERIES);
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDto)))
                .andExpect(status().isBadRequest());
    }

    public LoanApplicationRequestDto CreateLoanApplicationRequestDto(Integer term, Integer amount, String passportSeries) {
        return LoanApplicationRequestDto.builder()
                .firstName("Darth")
                .lastName("Vader")
                .middleName("Lord")
                .amount(BigDecimal.valueOf(amount))
                .birthdate(LocalDate.now().minusYears(30))
                .passportNumber("111111")
                .passportSeries(passportSeries)
                .term(term)
                .email("LordVader@gmail.com")
                .build();
    }

    public LoanOfferDto createLoanOfferDto() {
        return LoanOfferDto.builder()
                .applicationId(5L)
                .requestedAmount(BigDecimal.valueOf(300000))
                .totalAmount(BigDecimal.valueOf(312000))
                .term(24)
                .monthlyPayment(BigDecimal.valueOf(14833.05))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .rate(BigDecimal.valueOf(13))
                .build();
    }
}
