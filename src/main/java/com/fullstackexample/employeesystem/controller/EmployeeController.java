package com.fullstackexample.employeesystem.controller;

import com.fullstackexample.employeesystem.exception.EmployeeNotFoundException;
import com.fullstackexample.employeesystem.model.Employee;
import com.fullstackexample.employeesystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @PostMapping("/employee")
    Employee newEmployee(@RequestBody Employee newEmployee){
        return employeeRepository.save(newEmployee);
    }
    @GetMapping("/employees")
    List<Employee>getAllEmployees(){
        return employeeRepository.findAll();
    }
    @GetMapping("employee/{id}")
    Employee getEmployeeById(@PathVariable int id){
        return employeeRepository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException(id));
    }

    @PutMapping("/employee/{id}")
    Employee update(@RequestBody Employee newEmployee,@PathVariable int id){
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setAddress(newEmployee.getAddress());
                    return employeeRepository.save(employee);
                }).orElseThrow(()->new EmployeeNotFoundException(id));
    }

    @DeleteMapping("/employee/{id}")
    String deleteEmployee(@PathVariable int id){
        if(!employeeRepository.existsById(id)){
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
        return "Employee with id "+id+" has been deleted successfully";
    }

}
