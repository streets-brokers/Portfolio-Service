package com.streeBrokers.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "order_set")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long orderId;
    public Long clientId;
    public String product;
    public boolean isShort;
    public int quantity;
    public double value;
    public long timestamp;
    public long updatedAt;
    public String side;
    public double marketPrice;
    public double price;
    public String status;
    public int xid;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "holding_id")
//    private Holding holding;
}