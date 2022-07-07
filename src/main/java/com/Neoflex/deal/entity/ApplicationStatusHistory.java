package com.Neoflex.deal.entity;

import com.Neoflex.deal.model.ChangeType;
import com.Neoflex.deal.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class ApplicationStatusHistory {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "id", nullable = false)
     private Long id;
     private Status status;
     private LocalDateTime time;
     private ChangeType changeType;
     @ManyToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "application", nullable = false)
     private Application application;

}
