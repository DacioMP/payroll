package com.pedrosa.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pedrosa.demo.entities.Employee;
import com.pedrosa.demo.entities.Order;
import com.pedrosa.demo.enums.OrderStatus;
import com.pedrosa.demo.repositories.EmployeeRepository;
import com.pedrosa.demo.repositories.OrderRepository;

@Configuration
public class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
    return args -> {
      log.info("Preloading " + employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar")));
      log.info("Preloading " + employeeRepository.save(new Employee("Frodo", "Baggins", "thief")));

      employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));

      orderRepository.save(new Order("MacBook Pro", OrderStatus.COMPLETED.toString()));
      orderRepository.save(new Order("iPhone", OrderStatus.IN_PROGRESS.toString()));

      orderRepository.findAll().forEach(order -> {
        log.info("Preloaded " + order);
      });
    };
  }
}
