package com.jit.robert.serviceimpl;

import com.jit.robert.domain.User;
import com.jit.robert.dto.TechnologyDTO;
import com.jit.robert.mapper.UserMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.UserService;
import com.jit.robert.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yin on 2017/10/12.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Value("${image.url}")
    private String url;
    @Override
    public String getUserImage(String username) {
        return userMapper.getUserImage(username);
    }

    @Override
    public void updateUserImage(String username, String image) {
        userMapper.updateUserImage(username,image);
    }

    /**
     * 修改密码
     * @param newPassword
     * @param oldPassword
     * @return
     */
    @Override
    public Boolean updatePassword(String oldPassword, String newPassword) {
        if (StringUtil.isEmpty(oldPassword) || StringUtil.isEmpty(newPassword)){
            throw new BusinessException(ResultCode.PARAM_IS_BLANK);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userMapper.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(oldPassword,currentUser.getPassword())){
            throw new BusinessException(ResultCode.USER_HAS_ERROR_PASSWORD);
        }
        int flag = userMapper.updatePassword(passwordEncoder.encode(newPassword),username);
        if (flag>0){
            return true;
        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }

    }

    @Override
    public User getUserById(Integer id) {
        User user = userMapper.getUserById(id);
        changeImage(user);
        return user;
    }
    private void changeImages(List<User> list){
        for (int i=0;i<list.size();i++){
            changeImage(list.get(i));
        }
    }

    private void changeImage(User obj){
        if (obj.getImage()!=null){
            obj.setImage(url+obj.getImage());
        }
    }
}
