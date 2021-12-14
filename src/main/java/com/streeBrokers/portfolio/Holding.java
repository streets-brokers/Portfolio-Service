//package com.streeBrokers.portfolio;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.client.RestTemplate;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//@Table(name = "holding")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//public class Holding {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long holdingId;
//    private String symbol;
//    private Integer quantity = 0;
//    private BigDecimal purchaseValue = BigDecimal.ZERO;
//    private BigDecimal sellValue = BigDecimal.ZERO;
//    private BigDecimal currentValue = BigDecimal.ZERO;
//    @JsonIgnore
//    @OneToMany(
//            mappedBy = "holding",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private Set<Order> orders = new HashSet<>();
////    private BigDecimal currentValue = BigDecimal.ZERO;
//
//    @ManyToOne
//    @JoinColumn(name="portfolio_id")
//    private Portfolio portfolio;
//
////    public void addOrder(Order order){
////        if (orders.contains(order)) {
////            throw new IllegalStateException("Order already exists");
////        } else {
////            orders.add(order);
////            // update stats
////            if (order.getSide().equals("BUY")) {
////                setQuantity(getQuantity() + order.getQuantity());
////                setPurchaseValue(getPurchaseValue().add(new BigDecimal(order.getPrice()).multiply(new BigDecimal(order.getQuantity()))));
////            } else if (order.getSide().equals("SELL")) {
////                setQuantity(getQuantity() - order.getQuantity());
////                setSellValue(getSellValue().add(new BigDecimal(order.getPrice()).multiply(new BigDecimal(order.getQuantity()))));
////            }
////        }
////    }
//
//    /**
//     * Next things to do is to have an add order method
//     * You then check if the order exists in the orders set,
//     * if it does, you throw an error
//     * if it does not, you check
//     * whether orders(either buy-side or sell-side)
//     * if its is a buy-side,
//     * if it is,
//     * 1. You recalculate and set the Quantity of that trade order type
//     * 2. You recalculate and set the purchase value of that trade order type
//     *
//     * if its is a sell-side,
//     * if it is,
//     * 1. You recalculate and set the Quantity of that trade order type
//     * 2. You recalculate and set the purchase value of that trade order type
//     */
//
//
//}
