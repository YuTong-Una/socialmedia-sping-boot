package com.una.esunbank.socialmedia.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.una.esunbank.socialmedia.model.Posts;
import com.una.esunbank.socialmedia.service.CommentService;
import com.una.esunbank.socialmedia.service.PostsService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/post")
public class PostsController {

	@Autowired
	private PostsService postsService;
	
	@Autowired
	private CommentService commentService;
	
	// 查詢所有文章
	@GetMapping("/all")
    public String findAllPosts() {

        // 使用Service查詢
		List<Posts> postList = postsService.findAll();

        // 轉換會員資料為JSON格式
        JSONArray postJsonArr = new JSONArray();
        for (Posts tempPost : postList) {
            JSONObject item = new JSONObject();
            item.put("postId", tempPost.getPostId());
            item.put("content", tempPost.getContent());
            item.put("image", tempPost.getImage());
            item.put("createdAt", tempPost.getCreatedAt());
            item.put("user", tempPost.getUser().getUserName());
            item.put("userId", tempPost.getUser().getUserId());
            postJsonArr.put(item);
        }

        // 返回 JSON
        JSONObject responseJson = new JSONObject();
        responseJson.put("list", postJsonArr);

        System.err.println("responseJson: "+ responseJson);
        return responseJson.toString();
    }
	
	// postId 查詢一篇文章
	@GetMapping("/onePost")
	public String findPostById(@RequestParam Integer postId) {
		
		Posts tempPost = postsService.findPostById(postId);
		
	 	JSONObject responseJson = new JSONObject();
        JSONArray array = new JSONArray();

        // 查詢到，將其資料放入 responseJson 中
        JSONObject item = new JSONObject();
            	   item.put("postId", tempPost.getPostId());
            	   item.put("content", tempPost.getContent());
            	   item.put("image", tempPost.getImage());
            	   item.put("user", tempPost.getUser().getUserName());
            	   item.put("createdAt", tempPost.getCreatedAt());            	   
            array.put(item);	            
        responseJson = responseJson.put("list", array);
        System.out.println("controller"+responseJson);
        return responseJson.toString();
	}
	
	// 發文
	@PostMapping("/new")
    public ResponseEntity<String> newPost (HttpSession session, @RequestBody Posts posts) {
		
		Object userIdObj = session.getAttribute("userId");

		// 驗證登入
		if(userIdObj == null) {
			return ResponseEntity.badRequest().body("請先登入再使用發文功能");
		}
		
		
		// 接收資料		
		Integer userId = (Integer)userIdObj;
		String content = posts.getContent();
		
		// 驗證資料
		if (content == null || content.length() == 0 ) {
			return ResponseEntity.badRequest().body("請輸入完整資料");
		}

		// 呼叫service
		postsService.createPost(userId, posts);
		
		// 回傳結果view
		return ResponseEntity.ok("發文成功");
		
	}


    // 編輯文章
	@PostMapping("/update")
    public ResponseEntity<String> updatePost (HttpSession session, @RequestBody Posts posts) {
		
		try {
			Object userIdObj = session.getAttribute("userId");

			// 驗證登入
			if(userIdObj == null) {
				return ResponseEntity.badRequest().body("請先登入再使用編輯功能");
			}
			
			// 接收資料		
			
			Integer userId = (Integer)userIdObj;
			String content = posts.getContent();
			Integer postId = posts.getPostId();
			
			// 驗證資料
			if (postId == null) {
				return ResponseEntity.badRequest().body("出現錯誤，請稍後再試");
			}
			if (content == null || content.length() == 0 ) {
				return ResponseEntity.badRequest().body("請輸入完整資料");
			}

			// 呼叫 service 層進行更新
		    postsService.updatePost(userId, posts);
			
			// 回傳結果view
			return ResponseEntity.ok("修改成功");
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
		
	}
	
	// 刪除文章
	@Transactional
	@PostMapping("/delete")
    public ResponseEntity<String> deletePost (HttpSession session, @RequestBody Posts posts) {
		
		try {
			Object userIdObj = session.getAttribute("userId");

			// 驗證登入
			if(userIdObj == null) {
				return ResponseEntity.badRequest().body("請先登入再使用刪除功能");
			}
			
			Integer postId = posts.getPostId();
			
			// 驗證資料
			if (postId == null) {
				return ResponseEntity.badRequest().body("出現錯誤，請稍後再試");
			}
			
			Integer userId = (Integer)userIdObj;
			
			// 先刪除相關留言
	        commentService.deleteCommentsForPost(postId, userId);
			
			
			// 呼叫 service 層進行更新
		    postsService.deletePost(userId, posts);
			
			// 回傳結果view
			return ResponseEntity.ok("刪除成功");
			
			
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
		
		
	}
    
}
