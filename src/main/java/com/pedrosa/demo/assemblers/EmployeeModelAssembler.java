package com.pedrosa.demo.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.pedrosa.demo.controllers.EmployeeController;
import com.pedrosa.demo.entities.Employee;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

  @Override
  public EntityModel<Employee> toModel(Employee employee) {
    return EntityModel.of(
        employee,
        linkTo(methodOn(EmployeeController.class).findById(employee.getId())).withSelfRel(),
        linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees"));
  }
}
