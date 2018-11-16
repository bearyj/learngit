package com.jit.robert.serviceinterface;

import com.jit.robert.domain.User;
import com.jit.robert.dto.UserDTO;

public interface AuthService {
    User register(User userToAdd);
    UserDTO login(String username, String password);
    String refresh(String oldToken);
}

