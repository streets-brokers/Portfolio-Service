package com.streeBrokers.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "portfolio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long portfolioId;
    private Long clientId;
    private String name;
    private BigDecimal currentTotalValue = BigDecimal.ZERO;
    private BigDecimal purchaseValue = BigDecimal.ZERO;
    private BigDecimal sellValue = BigDecimal.ZERO;

//    @JsonIgnore
//    @OneToMany(
//            mappedBy = "portfolio",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private Map<String, Holding> holdings = new HashMap<>();
//

//    public void addHolding(Holding holding) {
//        holdings.put(holding.getSymbol(), holding);
//    }
//    public Holding getHolding(String symbol) {
//        return holdings.get(symbol);
//    }
//
//    public void refreshTotalValue() {
//        this.currentTotalValue = BigDecimal.ZERO;
//        this.purchaseValue = BigDecimal.ZERO;
//        this.sellValue = BigDecimal.ZERO;
//        holdings.values().forEach(holding -> {
//            this.currentTotalValue = this.currentTotalValue.add(holding.getCurrentValue().multiply(new BigDecimal(holding.getQuantity())));
//            this.purchaseValue = this.purchaseValue.add(holding.getPurchaseValue());
//            this.sellValue = this.sellValue.add(holding.getSellValue());
//        });
//    }

}
