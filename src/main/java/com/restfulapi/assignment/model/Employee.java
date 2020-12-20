package com.restfulapi.assignment.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class of Employee
 * 
 * @author amit chauhan
 */

@ApiModel(description = "Employee details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
	@Id
	private Long id;
	private String name;
}
