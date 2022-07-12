package com.Neoflex.deal.entity;

import com.Neoflex.deal.model.Status;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client", nullable = false)
    private Client client;
    @OneToOne(mappedBy = "application")
    private Credit credit;
    private Status status;
    private LocalDate creationDate;
    @OneToOne(mappedBy = "application")
    private LoanOffer appliedOffer;
    private LocalDate signDate;
    private String sesCode;
    @OneToMany(mappedBy = "application")
    private List<ApplicationStatusHistory>  applicationStatusHistories;
}
