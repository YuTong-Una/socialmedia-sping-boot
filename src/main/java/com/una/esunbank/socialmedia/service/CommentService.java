package com.una.esunbank.socialmedia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.una.esunbank.socialmedia.dto.CommentDTO;
import com.una.esunbank.socialmedia.model.Comment;
import com.una.esunbank.socialmedia.model.Posts;
import com.una.esunbank.socialmedia.model.Users;
import com.una.esunbank.socialmedia.repository.CommentsRepository;
import com.una.esunbank.socialmedia.repository.PostsRepository;
import com.una.esunbank.socialmedia.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PostsRepository postsRepository;
	
	@Autowired
	private CommentsRepository commentsRepository;
	

	public List<CommentDTO> findCommentDTOByPostId(Integer postId) {
	    List<Comment> comments = commentsRepository.findCommentByPostId(postId);
	    List<CommentDTO> result = new ArrayList<>();

	    for (Comment comment : comments) {
	        CommentDTO dto = new CommentDTO();
	        dto.setCommentId(comment.getCommentId());
	        dto.setContent(comment.getContent());
	        dto.setCreatedAt(comment.getCreatedAt());

	        // 預設 user 不為 null，否則需加 null 檢查
	        dto.setUserName(comment.getUser().getUserName());

	        result.add(dto);
	    }

	    return result;
	}

	
	public void deleteCommentsForPost(Integer postId, Integer userId) {
	    Optional<Posts> existingPostOpt = postsRepository.findById(postId);
	    if (existingPostOpt.isEmpty()) {
	        throw new RuntimeException("找不到該文章");
	    }

	    Posts existingPost = existingPostOpt.get();

	    // 檢查該文章的創建者是否為當前登入的用戶
	    if (!existingPost.getUser().getUserId().equals(userId)) {
	        throw new RuntimeException("您無權刪除此文章");
	    }

	    // 使用正確的方法刪除相關聯的留言
	    commentsRepository.deleteCommentsByPostId(postId);
	}
	
	@Transactional
	public void createComment(Integer userId, Integer postId, Comment comment) {
		
		Optional<Users> userOpt = usersRepository.findById(userId);
		Users user = userOpt.get();
		
		Optional<Posts> postOpt = postsRepository.findById(postId);
		Posts post = postOpt.get();
		
        // 防止 XSS 攻擊     
        String sanitizedContent = HtmlUtils.htmlEscape(comment.getContent());
        comment.setContent(sanitizedContent);


        comment.setUser(user);
        comment.setPost(post);
        commentsRepository.save(comment);
		
	}
	
}
