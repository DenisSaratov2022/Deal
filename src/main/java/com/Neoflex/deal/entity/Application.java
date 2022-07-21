package com.Neoflex.deal.entity;

import com.Neoflex.deal.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client")
 //   @OneToOne(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Client client;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application")
    private Credit credit;
    private Status status;
    private LocalDate creationDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appliedOffer")
    private LoanOffer appliedOffer;
    private LocalDate signDate;
    private String sesCode;
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<ApplicationStatusHistory> applicationStatusHistories;
}
