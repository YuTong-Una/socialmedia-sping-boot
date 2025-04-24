package com.una.esunbank.socialmedia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.una.esunbank.socialmedia.dto.UsersDTO;
import com.una.esunbank.socialmedia.model.Users;
import com.una.esunbank.socialmedia.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	//註冊
	@Transactional
	public void registerUser(UsersDTO usersDTO) {
        // 檢查 email 是否已經註冊
        Optional<Users> existingEmail = usersRepository.findByEmail(usersDTO.getEmail());
        if (existingEmail.isPresent()) {
            throw new RuntimeException("此Email已被註冊過");
        }

        // 檢查手機號碼是否重複
        
        Optional<Users> existingPhone = usersRepository.findByPhoneNumber(usersDTO.getPhoneNumber());
        if (existingPhone.isPresent()) {
            throw new RuntimeException("此手機號碼已被註冊過");
        }
        
        // 密碼加密
        String encryptedPassword = passwordEncoder.encode(usersDTO.getPassword());
        
        // 建立 Users 實體
        Users newUser = new Users();
        newUser.setUserName(usersDTO.getUserName());
        newUser.setPhoneNumber(usersDTO.getPhoneNumber());
        newUser.setEmail(usersDTO.getEmail());
        newUser.setPassword(encryptedPassword);
        newUser.setBiography(usersDTO.getBiography());
        newUser.setCoverImage(usersDTO.getCoverImage());

        // 儲存到資料庫
        usersRepository.save(newUser);
        
        
    }
	
	
	//登入
	@Transactional
    public boolean login(UsersDTO usersDTO) {
		System.out.println("登入service");
        // 查詢資料庫中的使用者
        Optional<Users> userOpt = usersRepository.findByPhoneNumber(usersDTO.getPhoneNumber());
        
      
        
        if (userOpt.isEmpty()) {
            return false;
        }

        Users user = userOpt.get();
        
        
        usersDTO.setUserId(user.getUserId());

        // 比對密碼
        // 使用 BCryptPasswordEncoder 比對密碼
        return passwordEncoder.matches(usersDTO.getPassword(), user.getPassword());
    }
	
	
}
