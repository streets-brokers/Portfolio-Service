package com.streeBrokers.portfolio;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class MarketData {
    public int id;
    public String ticker;
    public String xchange;
    public long timestamp;
    public int sellLimit;
    public double maxPriceShift;
    public double askPrice;
    public double bidPrice;
    public int buyLimit;
    public double lastTradedPrice;
}
