package com.restfulapi.assignment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import com.restfulapi.assignment.exception.EmployeeNotFoundException;
import com.restfulapi.assignment.model.Employee;
import com.restfulapi.assignment.repository.EmployeeRepository;

@RunWith(JUnit4.class)
public class EmployeeControllerTest {

	EmployeeRepository employeeRepository;
	EmployeeController employeeController;

	@BeforeEach
	void setUp() {
		employeeRepository = Mockito.mock(EmployeeRepository.class);
		employeeController = new EmployeeController(employeeRepository);
	}

	@Test
	public void getEmployeesTest() {
		when(employeeRepository.findAll()).thenReturn(Stream
				.of(new Employee(5L, "employeTest1"), new Employee(6L, "employeTest2")).collect(Collectors.toList()));
		assertEquals(2, employeeRepository.findAll().size());
	}

	@Test
	public void fetchEmployeeByIDTest() {
		Employee employee = new Employee();
		employee.setId((long) 1);
		employee.setName("amit");
		when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
		Employee emp = employeeController.fetchEmployeeByID(1);
		assertEquals(1, emp.getId());
	}

	@Test
	public void createEmployeeTest() {
		Employee employee = new Employee();
		employee.setId((long) 1);
		employee.setName("amit");
		when(employeeRepository.save(any())).thenReturn(employee);
		ResponseEntity<Employee> emp = employeeController.createEmployee(employee);
		assertEquals(1, emp.getBody().getId());
	}

	@Test
	public void updateEmployeeTest() {
		Employee employee = new Employee();
		employee.setId((long) 1);
		employee.setName("amit");
		when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
		when(employeeRepository.save(any())).thenReturn(employee);
		ResponseEntity<Employee> emp = employeeController.updateEmployee(employee, 1);
		assertEquals(1, emp.getBody().getId());
	}

	@Test
	public void deleteEmployeeTest() {
		Employee employee = new Employee();
		employee.setId((long) 1);
		employee.setName("amit");
		when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
		employeeController.updateEmployee(employee, 1);
		assertEquals(1, employee.getId());
	}

	@Test
	public void deleteEmployeeExceptionTest() {

		Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
			employeeController.deleteEmployee(1);
		});
	}
}
