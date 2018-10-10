package com.corey.javaretake.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.corey.javaretake.models.User;
import com.corey.javaretake.models.Show;

@Repository
public interface ShowRepository extends CrudRepository<Show, Long>, PagingAndSortingRepository<Show, Long>{
	List<Show> findAll();
	Show findByTitle(String title);
}
