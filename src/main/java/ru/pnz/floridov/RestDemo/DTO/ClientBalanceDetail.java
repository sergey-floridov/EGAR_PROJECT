package ru.pnz.floridov.RestDemo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientBalanceDetail {

    private BigDecimal rubDebetSum;
    private BigDecimal usdDebetSum;
    private BigDecimal euroDebetSum;
    private BigDecimal rubCreditSum;
    private BigDecimal usdCreditSum;
    private BigDecimal euroCreditSum;

    private BigDecimal totalDebet;
    private BigDecimal totalCredit;

    private BigDecimal total;
}
