package com.streeBrokers.portfolio;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioHolding {
    private Portfolio portfolio;
    private Map<String, HoldingObj> holdingObjMap;
}
