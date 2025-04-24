package com.una.esunbank.socialmedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.una.esunbank.socialmedia.model.Comment;

import jakarta.transaction.Transactional;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer>{

	
	@Query(value ="select * from Comment a where a.post_id = :postId", nativeQuery = true)
	List<Comment> findCommentByPostId(@Param("postId") Integer postId);
	
	@Modifying
	@Transactional
	@Query(value ="DELETE FROM Comment WHERE post_id = :postId", nativeQuery = true)
	void deleteCommentsByPostId(@Param("postId") Integer postId);
	

}
