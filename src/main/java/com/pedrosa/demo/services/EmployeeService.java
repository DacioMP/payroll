package com.pedrosa.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pedrosa.demo.EmployeeNotFoundException;
import com.pedrosa.demo.entities.Employee;
import com.pedrosa.demo.repositories.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

  private final EmployeeRepository repository;

  public EmployeeService(EmployeeRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public List<Employee> findAll() {
    return repository.findAll();
  }

  @Transactional(readOnly = true)
  public Employee findById(Long id) {
    Optional<Employee> employee = repository.findById(id);
    return employee.orElseThrow(() -> new EmployeeNotFoundException(id));
  }

  @Transactional
  public Employee insert(Employee employee) {
    return repository.save(employee);
  }

  @Transactional
  public Employee update(Long id, Employee newEmployee) {
    try {
      Employee employee = repository.getReferenceById(id);
      employee.setName(newEmployee.getName());
      employee.setRole(newEmployee.getRole());
      return repository.save(employee);

    } catch (EntityNotFoundException e) {
      return repository.save(newEmployee);
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
