package com.jit.robert.util;

import com.jit.robert.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

public class UserUtil {

    public static User RegisterUser(String username){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Date date = new Date();
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode("123456"));
        user.setRegistertime(date);
        return user;
    }
}
