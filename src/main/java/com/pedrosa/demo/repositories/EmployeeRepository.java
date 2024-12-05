package com.pedrosa.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrosa.demo.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
