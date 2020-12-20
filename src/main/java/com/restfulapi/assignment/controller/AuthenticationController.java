package com.restfulapi.assignment.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.restfulapi.assignment.config.JwtTokenUtil;
import com.restfulapi.assignment.model.JwtRequest;
import com.restfulapi.assignment.model.JwtResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * class will Authenticate and generate JWT Token
 * 
 * @author amit chauhan
 */

@RestController
@CrossOrigin
@Api(tags = "User Authenticate and generate JWT Token Api")
public class AuthenticationController {

	Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	/**
	 * method for authenticate user and return the JWT Token
	 * 
	 * @param authenticationRequest
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "User Authenticate and generate JWT Token Api")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		logger.info("JwtRequest :" + authenticationRequest);
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	/**
	 * This method authenticate user with specified username and password
	 * 
	 * @param username the username string to use for authentication
	 * @param password the password string to use for authentication
	 * @throws Exception To catch exception when specified invalid user credentials
	 *                   or disabled user credentials
	 */
	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER DISABLE :", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID CREDENTIALS :", e);
		}
	}
}
