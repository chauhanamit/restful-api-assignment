package com.restfulapi.assignment.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfulapi.assignment.exception.EmployeeNotFoundException;
import com.restfulapi.assignment.model.Employee;
import com.restfulapi.assignment.repository.EmployeeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * EmployeeController class have Employee Rest Api
 * 
 * @author amit chauhan
 */

@RestController
@Api(tags = "Employee detail Apis")
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	private EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	/**
	 * return all employees details
	 * 
	 * @return list of employees
	 */
	@ApiOperation(value = "Fetch All Employee details")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@GetMapping(path = "/employees")
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

	/**
	 * fetching employee details using employee Id
	 * 
	 * @param id
	 * @return employee object based on specified id
	 */
	@ApiOperation(value = "Fetch Employee details using Employee Id")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@GetMapping("/employees/{id}")
	public Employee fetchEmployeeByID(
			@ApiParam(value = "Employee Id", defaultValue = "1", required = true) @PathVariable long id) {
		logger.info("fetchEmployeeByID Employee id :" + id);
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			return employee.get();
		} else {
			throw new EmployeeNotFoundException("id-" + id);
		}

	}

	/**
	 * save employee details into DB
	 * 
	 * @param emp
	 * @return Response entity object with created location
	 */
	@ApiOperation(value = "Save Employee details")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@PostMapping("/employees")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		logger.info("createEmployee Employee data :" + employee);
		Employee savedEmp = employeeRepository.save(employee);
		return ResponseEntity.ok().body(savedEmp);
	}

	/**
	 * update employee details using employee Id
	 * 
	 * @param emp
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Update Employee details using Employee Id")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee,
			@ApiParam(value = "Employee Id", defaultValue = "1", required = true) @PathVariable long id) {
		logger.info("updateEmployee Employee data :" + employee);
		Optional<Employee> employeeOptional = employeeRepository.findById(id);

		if (employeeOptional.isPresent()) {
			employee.setId(id);
			employeeRepository.save(employee);
			return ResponseEntity.ok().body(employee);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	/**
	 * delete employee by employee Id
	 * 
	 * @param employee details will be delete using employee Id
	 */
	@ApiOperation(value = "Delete Employee details using Employee Id")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@DeleteMapping("/employees/{id}")
	public void deleteEmployee(
			@ApiParam(value = "Employee Id", defaultValue = "1", required = true) @PathVariable long id) {
		logger.info("deleteEmployee Employee id :" + id);
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			employeeRepository.deleteById(id);
		} else {
			throw new EmployeeNotFoundException("id-" + id);

		}
	}

}
