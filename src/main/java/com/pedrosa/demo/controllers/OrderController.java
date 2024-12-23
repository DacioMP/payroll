package com.pedrosa.demo.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedrosa.demo.assemblers.OrderModelAssembler;
import com.pedrosa.demo.entities.Order;
import com.pedrosa.demo.enums.OrderStatus;
import com.pedrosa.demo.services.OrderService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

  private final OrderService service;
  private final OrderModelAssembler orderModelAssembler;

  OrderController(OrderService service, OrderModelAssembler orderModelAssembler) {
    this.service = service;
    this.orderModelAssembler = orderModelAssembler;
  }

  @GetMapping
  public CollectionModel<EntityModel<Order>> findAll() {

    List<EntityModel<Order>> orders = service.findAll()
        .stream()
        .map(orderModelAssembler::toModel)
        .toList();

    return CollectionModel.of(
        orders,
        linkTo(methodOn(OrderController.class).findAll()).withSelfRel());
  }

  @GetMapping(value = "/{id}")
  public EntityModel<Order> findById(@PathVariable Long id) {
    Order order = service.findById(id);
    return orderModelAssembler.toModel(order);
  }

  @PostMapping()
  public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {

    Order newOrder = service.insert(order);

    return ResponseEntity
        .created(linkTo(methodOn(OrderController.class).findById(newOrder.getId())).toUri())
        .body(orderModelAssembler.toModel(newOrder));
  }

  @DeleteMapping(value = "/{id}/cancel")
  public ResponseEntity<?> cancel(@PathVariable Long id) {

    Order order = service.findById(id);

    if (order.getStatus() == OrderStatus.IN_PROGRESS) {
      order = service.cancel(order);
      return ResponseEntity.ok(orderModelAssembler.toModel(order));
    }

    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(Problem
            .create().withTitle("Method not allowed")
            .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
  }

  @PutMapping(value = "/{id}/complete")
  public ResponseEntity<?> complete(@PathVariable Long id) {

    Order order = service.findById(id);

    if (order.getStatus() == OrderStatus.IN_PROGRESS) {
      // order.setStatus(OrderStatus.COMPLETED);
      order = service.complete(order);
      return ResponseEntity.ok(orderModelAssembler.toModel(order));
    }

    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(Problem
            .create().withTitle("Method not allowed")
            .withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
  }
}
