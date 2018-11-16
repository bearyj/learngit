package com.jit.robert.serviceinterface;

import com.jit.robert.domain.User;

public interface UserService {

    String getUserImage(String username);

    void updateUserImage(String username, String image);

    Boolean updatePassword(String oldPassword,String newPassword);

    User getUserById(Integer id);
}
