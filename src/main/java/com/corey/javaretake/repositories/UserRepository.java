package com.corey.javaretake.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.corey.javaretake.models.Show;
import com.corey.javaretake.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	List<User> findAll();
	User findByEmail(String email);
	User findByName(String name);
}
