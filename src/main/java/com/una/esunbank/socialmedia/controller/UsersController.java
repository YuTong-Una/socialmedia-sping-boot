package com.una.esunbank.socialmedia.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.una.esunbank.socialmedia.dto.UsersDTO;
import com.una.esunbank.socialmedia.service.UsersService;
import com.una.esunbank.socialmedia.utils.UserValidator;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersService usersService;

    // 註冊
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsersDTO usersDTO) {
        
        // 驗證必填欄位
        if (usersDTO.getUserName() == null || usersDTO.getUserName().isEmpty() ||
            usersDTO.getPhoneNumber() == null || usersDTO.getPhoneNumber().isEmpty() ||
            usersDTO.getPassword() == null || usersDTO.getPassword().isEmpty() ||
            usersDTO.getEmail() == null || usersDTO.getEmail().isEmpty() ||
            usersDTO.getBiography() == null || usersDTO.getBiography().isEmpty()) {
            return ResponseEntity.badRequest().body("所有欄位均為必填");
        }
        

        // 驗證手機號碼格式
        if (!UserValidator.validatePhoneNumber(usersDTO.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("手機號碼格式不正確");
        }

        // 驗證電子郵件格式
        if (!UserValidator.validateEmail(usersDTO.getEmail())) {
            return ResponseEntity.badRequest().body("電子郵件格式不正確");
        }

        // 驗證密碼格式
        if (!UserValidator.validatePassword(usersDTO.getPassword())) {
            return ResponseEntity.badRequest().body("密碼格式不正確，必須包含至少1個大寫字母、1個小寫字母和1個數字");
        }

        try {
            usersService.registerUser(usersDTO); // 呼叫 service 層處理註冊
            return ResponseEntity.ok("註冊成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("註冊失敗：" + e.getMessage());
        }
    }
	
    // 登入
    @PostMapping("/login")
    public String login(HttpSession session, @RequestBody UsersDTO usersDTO) {
    	
    	JSONObject responseJson = new JSONObject();

    	// 驗證必填欄位
    	if (usersDTO.getPhoneNumber() == null || usersDTO.getPassword() == null) {
        	responseJson.put("success", false);
			responseJson.put("message", "請輸入帳號/密碼");
			return responseJson.toString();
        }

        boolean success = usersService.login(usersDTO);
        
        if (success) {
        	System.out.println(usersDTO.getUserId());
			session.setAttribute("userId", usersDTO.getUserId());  // 存入 session
			responseJson.put("success", true);
			responseJson.put("message", "登入成功");
			responseJson.put("userId", usersDTO.getUserId());
        } else {
        	responseJson.put("success", false);
			responseJson.put("message", "登入失敗");
        }
        return responseJson.toString();
    }
    
}
