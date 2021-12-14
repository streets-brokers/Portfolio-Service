package com.streeBrokers.portfolio;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HoldingObj {
    Integer quantity = 0;
    BigDecimal purchaseValue = BigDecimal.ZERO;
    BigDecimal sellValue = BigDecimal.ZERO;
    BigDecimal currentValue = BigDecimal.ZERO;
}
