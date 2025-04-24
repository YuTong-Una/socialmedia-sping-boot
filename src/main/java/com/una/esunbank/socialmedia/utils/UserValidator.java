package com.una.esunbank.socialmedia.utils;

import java.util.regex.Pattern;

public class UserValidator {

	 // 驗證手機號碼格式
    public static boolean validatePhoneNumber(String phoneNumber) {
        // 台灣手機號碼格式
        String phoneRegex = "^09\\d{8}$";
        return Pattern.matches(phoneRegex, phoneNumber);
    }

    // 驗證電子郵件格式
    public static boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }

    // 驗證密碼格式
    public static boolean validatePassword(String password) {
        // 密碼必須包含至少8個字元，其中至少包含1個大寫字母，1個小寫字母，1個數字
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return Pattern.matches(passwordRegex, password);
    }
    
}
