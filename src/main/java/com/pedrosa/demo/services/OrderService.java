package com.pedrosa.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedrosa.demo.entities.Order;
import com.pedrosa.demo.enums.OrderStatus;
import com.pedrosa.demo.repositories.OrderRepository;
import com.pedrosa.demo.services.exceptions.OrderNotFoundException;

@Service
public class OrderService {

  private final OrderRepository repository;

  public OrderService(OrderRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public List<Order> findAll() {
    return repository.findAll();
  }

  @Transactional(readOnly = true)
  public Order findById(Long id) {
    Optional<Order> order = repository.findById(id);
    return order.orElseThrow(() -> new OrderNotFoundException(id));
  }

  @Transactional
  public Order insert(Order order) {
    order.setStatus(OrderStatus.IN_PROGRESS);
    return repository.save(order);
  }

  @Transactional
  public Order cancel(Order order) {
    order.setStatus(OrderStatus.CANCELLED);
    return repository.save(order);
  }

  @Transactional
  public Order complete(Order order) {
    order.setStatus(OrderStatus.COMPLETED);
    return repository.save(order);
  }
}
