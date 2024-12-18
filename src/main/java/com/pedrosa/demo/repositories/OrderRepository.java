package com.pedrosa.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrosa.demo.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
