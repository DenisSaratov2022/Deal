package com.Neoflex.deal.entity;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String passportSeries;
    private String passportNumber;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;
    @OneToOne(mappedBy = "passport")
    private Client client;

}
