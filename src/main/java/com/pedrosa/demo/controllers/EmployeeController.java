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
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;

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
  public ResponseEntity<?> insert(@RequestBody Employee newEmployee) {

    EntityModel<Employee> entityModel = employeeModelAssembler.toModel(employeeRepository.save(newEmployee));

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Employee newEmployee) {
    Employee updatedEmployee = employeeRepository.findById(id)
        .map(employee -> {
          employee.setName(newEmployee.getName());
          employee.setRole(newEmployee.getRole());
          return employeeRepository.save(employee);
        })
        .orElseGet(() -> {
          return employeeRepository.save(newEmployee);
        });

    EntityModel<Employee> entityModel = employeeModelAssembler.toModel(updatedEmployee);

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    employeeRepository.deleteById(id);

    return ResponseEntity.noContent().build();
  }
}
