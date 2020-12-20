package com.restfulapi.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfulapi.assignment.model.Employee;

/** Employee Repository interface
 * @author amit chauhan
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
