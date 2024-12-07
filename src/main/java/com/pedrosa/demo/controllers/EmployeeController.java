package com.pedrosa.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedrosa.demo.EmployeeNotFoundException;
import com.pedrosa.demo.assemblers.EmployeeModelAssembler;
import com.pedrosa.demo.entities.Employee;
import com.pedrosa.demo.repositories.EmployeeRepository;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

  private final EmployeeRepository employeeRepository;
  private final EmployeeModelAssembler employeeModelAssembler;

  public EmployeeController(EmployeeRepository employeeRepository, EmployeeModelAssembler employeeModelAssembler) {
    this.employeeRepository = employeeRepository;
    this.employeeModelAssembler = employeeModelAssembler;
  }

  @GetMapping
  public CollectionModel<EntityModel<Employee>> findAll() {

    List<EntityModel<Employee>> employees = employeeRepository.findAll()
        .stream()
        .map(employeeModelAssembler::toModel)
        .toList();

    return CollectionModel.of(
        employees,
        linkTo(methodOn(EmployeeController.class).findAll()).withSelfRel());
  }

  @GetMapping(value = "/{id}")
  public EntityModel<Employee> findById(@PathVariable Long id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException(id));

    return employeeModelAssembler.toModel(employee);
  }

  @PostMapping
  public Employee insert(@RequestBody Employee obj) {
    return employeeRepository.save(obj);
  }

  @PutMapping(value = "/{id}")
  public Employee update(@PathVariable Long id, @RequestBody Employee obj) {
    return employeeRepository.findById(id)
        .map(employee -> {
          employee.setName(obj.getName());
          employee.setRole(obj.getRole());
          return employeeRepository.save(employee);
        })
        .orElseGet(() -> {
          return employeeRepository.save(obj);
        });
  }

  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    employeeRepository.deleteById(id);
  }
}
