package com.restfulapi.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.restfulapi.assignment.model.User;
import com.restfulapi.assignment.repository.UserRepository;

@RunWith(JUnit4.class)
public class JwtUserDetailsServiceTest {

	JwtUserDetailsService jwtUserDetailsService;
	UserRepository userRepository;

	@BeforeEach
	void setUp() {
		userRepository = Mockito.mock(UserRepository.class);
		jwtUserDetailsService = new JwtUserDetailsService(userRepository);
	}

	@Test
	public void loadUserByUsernameSuccessTest() {
		User user = new User();
		user.setId((long) 1);
		user.setUsername("user1");
		user.setPassword("12345");

		when(userRepository.findByUsername(any())).thenReturn(user);

		UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("user1");
		assertEquals("user1", userDetails.getUsername());
	}

	@Test
	public void loadUserByUsernameExceptionTest() {
		User user = null;
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			jwtUserDetailsService.loadUserByUsername("user1");
		});
		assertEquals(null, user);
	}
}
