package com.una.esunbank.socialmedia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.una.esunbank.socialmedia.model.Posts;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer>{

	Optional<Posts> findById(Integer postId);
	
}
