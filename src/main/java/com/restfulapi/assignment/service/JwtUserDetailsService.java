package com.restfulapi.assignment.service;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.restfulapi.assignment.model.User;
import com.restfulapi.assignment.repository.UserRepository;

/**
 * class implements user details service interface which loads user-specific
 * data.
 * 
 * @author amit chauhan
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

	private UserRepository userRepository;

	@Autowired
	public JwtUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("loadUserByUsername username :" + username);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Username not found: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());

	}
}