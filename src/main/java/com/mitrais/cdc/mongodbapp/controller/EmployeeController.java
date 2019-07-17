package com.mitrais.cdc.mongodbapp.controller;

import com.mitrais.cdc.mongodbapp.model.Employee;
import com.mitrais.cdc.mongodbapp.repository.IEmployeeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController extends CrossOriginController{

    @Autowired
    IEmployeeRepository employeeRepository;

    @RequestMapping("/employee")
    public Employee getEmployeeById(@RequestParam("id") ObjectId id){
        return employeeRepository.findBy_id(id);
    }

    @RequestMapping("/all-employee")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @RequestMapping(value="/employee", method= RequestMethod.POST)
    public Employee AddEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }
}
