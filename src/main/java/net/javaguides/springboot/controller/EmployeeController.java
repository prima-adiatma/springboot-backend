package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    // @Autowired to inject this repository by spring
    @Autowired
    private EmployeeRepository employeeRepository;

    // ----- Get All Employees -----
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // ----- Create Employees Rest API -----
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // ----- Get Employee by id rest api -----
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeByID(@PathVariable Long id) {

        // Retrieve employee from database
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id" + id));
        // Retrieve employee from database

        return ResponseEntity.ok(employee);
    }

    // ----- Update Employee Rest API -----
    // Long id is method argument
    // Employee employee basically client sends updated employee object in request Body
    // @RequestBody Directly map a request json object into java object Employee employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {

        // Retrieve employee from database
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id" + id));
        // Retrieve employee from database

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        // Employee updateEmployee is store the update employee object in a separate employee variable and return this object to the client

        // Employee object into a database
        Employee updateEmployee = employeeRepository.save(employee);
        // Employee object into a database

        return ResponseEntity.ok(updateEmployee);
    }

    // ----- Delete Employee Rest API -----

    // String is key, Boolean is value
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {

        // Retrieve employee from database
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id" + id));
        // Retrieve employee from database

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", true);
        return ResponseEntity.ok(response);
    }


}
