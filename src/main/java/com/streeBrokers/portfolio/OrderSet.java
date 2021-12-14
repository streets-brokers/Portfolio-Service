package com.streeBrokers.portfolio;

import lombok.*;

import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderSet {
    private Set<Order> orders;

//    @ManyToMany
//    Set<Holding> orderHolding;
}
