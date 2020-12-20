package com.restfulapi.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfulapi.assignment.model.User;

/** User Repository interface
 * @author amit chauhan
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
