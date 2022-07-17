package com.Neoflex.deal.controller;

import com.Neoflex.deal.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DealControllerTest {

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    @Test
    void controller_dealApplication_SuccessTest() throws Exception {
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
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDTO)))
                .andExpect(status().isOk());
    }
    @Test
    void controller_dealOffer_SuccessTest() throws Exception {
        LoanOfferDTO loanOfferDTO = LoanOfferDTO.builder()
                .applicationId(78L)
                .requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(BigDecimal.valueOf(100000))
                .term(12)
                .monthlyPayment(BigDecimal.valueOf(9002.25))
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .rate(BigDecimal.valueOf(14.5))
                .build();
        mockMvc.perform(put("/deal/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanOfferDTO)))
                .andExpect(status().isOk());
    }
    @Test
    void controller_dealApplication_SmallTermTest() throws Exception {
        LoanApplicationRequestDTO loanApplicationRequestDTO = LoanApplicationRequestDTO.builder()
                .firstName("Darth")
                .lastName("Vader")
                .middleName("Lord")
                .amount(BigDecimal.valueOf(10000))
                .birthdate(LocalDate.now().minusYears(30))
                .passportNumber("111111")
                .passportSeries("2222")
                .term(3)
                .email("LordVader@gmail.com")
                .build();
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void controller_dealApplication_SmallAmountTest() throws Exception {
        LoanApplicationRequestDTO loanApplicationRequestDTO = LoanApplicationRequestDTO.builder()
                .firstName("Darth")
                .lastName("Vader")
                .middleName("Lord")
                .amount(BigDecimal.valueOf(1000))
                .birthdate(LocalDate.now().minusYears(30))
                .passportNumber("111111")
                .passportSeries("2222")
                .term(3)
                .email("LordVader@gmail.com")
                .build();
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void controller_dealApplication_SmallPassportSeries() throws Exception {
        LoanApplicationRequestDTO loanApplicationRequestDTO = LoanApplicationRequestDTO.builder()
                .firstName("Darth")
                .lastName("Vader")
                .middleName("Lord")
                .amount(BigDecimal.valueOf(1000))
                .birthdate(LocalDate.now().minusYears(30))
                .passportNumber("111111")
                .passportSeries("222")
                .term(3)
                .email("LordVader@gmail.com")
                .build();
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDTO)))
                .andExpect(status().isBadRequest());
    }

}
