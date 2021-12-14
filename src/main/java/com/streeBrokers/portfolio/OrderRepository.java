package com.streeBrokers.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM order_set os WHERE os.client_id = ?1", nativeQuery = true)
    List<Order> viewListOfFulfilledOrders(Long clientId);
}
