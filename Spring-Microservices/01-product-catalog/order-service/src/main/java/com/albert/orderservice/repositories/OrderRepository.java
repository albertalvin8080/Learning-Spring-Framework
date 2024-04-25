package com.albert.orderservice.repositories;

import com.albert.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>
{
}
