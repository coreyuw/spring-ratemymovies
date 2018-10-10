package com.corey.javaretake.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.corey.javaretake.models.Rating;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {
	List<Rating> findAll();
	Rating findByUserid(Long uId);
}
