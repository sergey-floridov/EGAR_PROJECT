package ru.pnz.floridov.RestDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;
import ru.pnz.floridov.RestDemo.util.CreditType;
import ru.pnz.floridov.RestDemo.util.Currency;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Credit_product")
public class CreditProduct {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "current_account", unique = true)
    private Long currentAccount;


    @Column(name = "type")
    private CreditType creditType;


    @Min(2)
    @Max(300)
    @Column(name = "loan_period_in_month")
    private Integer loanPeriodInMonth;


    @Column(name = "amount")
    private BigDecimal amount;


    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;


    @Column(name = "rate")
    private BigDecimal rate;


    @Column(name = "insurance")
    private String insurance;


    @ManyToOne(fetch = FetchType.LAZY)
    @ToStringExclude
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;


}
