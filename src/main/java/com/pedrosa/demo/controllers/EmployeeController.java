package com.pedrosa.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedrosa.demo.EmployeeNotFoundException;
import com.pedrosa.demo.entities.Employee;
import com.pedrosa.demo.repositories.EmployeeRepository;

import java.util.List;

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

  public EmployeeController(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @GetMapping
  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  @GetMapping(value = "/{id}")
  public Employee findById(@PathVariable Long id) {
    return employeeRepository.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException(id));
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
