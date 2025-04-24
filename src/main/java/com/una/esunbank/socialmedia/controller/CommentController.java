package com.una.esunbank.socialmedia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.una.esunbank.socialmedia.dto.CommentDTO;
import com.una.esunbank.socialmedia.model.Comment;
import com.una.esunbank.socialmedia.service.CommentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	

	@GetMapping("/all")
	public List<CommentDTO> findCommentDTOByPostId(@RequestParam Integer postId) {
	    return commentService.findCommentDTOByPostId(postId);
	}
	
	// 新增留言
	@PostMapping("/new")
    public ResponseEntity<String> newComment (HttpSession session, @RequestBody Comment comment, @RequestParam Integer postId) {
		
		Object userIdObj = session.getAttribute("userId");

		// 驗證登入
		if(userIdObj == null) {
			return ResponseEntity.badRequest().body("請先登入再使用功能");
		}
		
		Integer userId = (Integer)userIdObj;
		
		// 驗證資料
		if (comment.getContent() == null || comment.getContent().length() == 0 ) {
			return ResponseEntity.badRequest().body("請輸入完整資料");
		}

		// 呼叫service
		commentService.createComment(userId, postId, comment);
		
		// 回傳結果view
		return ResponseEntity.ok("留言成功");
		
	}
   
    
}
