package com.una.esunbank.socialmedia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.una.esunbank.socialmedia.model.Posts;
import com.una.esunbank.socialmedia.model.Users;
import com.una.esunbank.socialmedia.repository.PostsRepository;
import com.una.esunbank.socialmedia.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class PostsService {
	
	@Autowired
	private PostsRepository postsRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	// 查詢所有文章
	public List<Posts> findAll() {
		List<Posts> list = postsRepository.findAll();
		return list;
	}
	
	// 用 postId 查詢文章
	public  Posts findPostById(Integer postId){
		Optional<Posts> opt = postsRepository.findById(postId);
		return opt.get();
	}
	
	// 發文
	@Transactional
	public void createPost(Integer userId,Posts posts) {
		
		Optional<Users> userOpt = usersRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("找不到使用者");
        }

        Users user = userOpt.get();
        
        // 防止 XSS 攻擊
        String sanitizedContent = HtmlUtils.htmlEscape(posts.getContent());
        posts.setContent(sanitizedContent);
        
        posts.setUser(user);
		postsRepository.save(posts);
		
	}
	
	// 編輯文章
	@Transactional
	public void updatePost(Integer userId, Posts updatePost) {
        
		Integer postId = updatePost.getPostId();
		
		Optional<Users> userOpt = usersRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("找不到使用者");
        }
        
        Optional<Posts> existingPostOpt = postsRepository.findById(postId);
        if (existingPostOpt.isEmpty()) {
            throw new RuntimeException("找不到該文章");
        }
        
        Posts existingPost = existingPostOpt.get();

        // 檢查該文章的創建者是否為當前登入的用戶
        if (!existingPost.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("您無權編輯此文章");
        }
        
		// 防止 XSS 攻擊
        String sanitizedContent = HtmlUtils.htmlEscape(updatePost.getContent());
        updatePost.setContent(sanitizedContent);
        
        // 更新內容
        if (updatePost.getContent() != null && !updatePost.getContent().isEmpty()) {
            existingPost.setContent(updatePost.getContent());
        }
        
        // 更新圖片（如果有傳圖片）
        if (updatePost.getImage() != null && !updatePost.getImage().isEmpty()) {
            existingPost.setImage(updatePost.getImage());
        }
        
        postsRepository.save(existingPost);
        
	}
	
	// 刪除文章
	public void deletePost(Integer userId, Posts post) {
		
		Integer postId = post.getPostId();
		
		Optional<Posts> existingPostOpt = postsRepository.findById(postId);
        if (existingPostOpt.isEmpty()) {
            throw new RuntimeException("找不到該文章");
        }
        
        Posts existingPost = existingPostOpt.get();

        // 檢查該文章的創建者是否為當前登入的用戶
        if (!existingPost.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("您無權刪除此文章");
        }
        
        postsRepository.delete(existingPost);
	}
	
}
