package ru.pnz.floridov.RestDemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.pnz.floridov.RestDemo.util.Currency;
import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Debet_account")
public class DebetAccount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "current_account")
    private Long currentAccount;


    @Column(name = "amount")
    private BigDecimal amount;


    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;


    @Column(name = "rate")
    private BigDecimal rate;


    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;


    @OneToOne(mappedBy = "debetAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Card card;


}

