package com.restfulapi.assignment.model;

import java.io.Serializable;
import lombok.Data;

/**
 * class implements a JWT token.
 * 
 * @author amit chauhan
 */

@Data
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
}