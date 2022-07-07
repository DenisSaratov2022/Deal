package com.Neoflex.deal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentScheduleElement {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "id", nullable = false)
     private Long id;
     private Integer number;
     private LocalDate date;
     private BigDecimal totalPayment;
     private BigDecimal interestPayment;
     private BigDecimal debtPayment;
     private BigDecimal remainingDebt;
     @ManyToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "credit", nullable = false)
     private Credit credit;


}
