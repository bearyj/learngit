package com.jit.robert.serviceimpl;

import com.jit.robert.domain.*;
import com.jit.robert.dto.UserDTO;
import com.jit.robert.mapper.*;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.security.JwtTokenUtil;
import com.jit.robert.security.JwtUser;
import com.jit.robert.serviceinterface.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service
public class AuthServiceImpl implements AuthService{

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TechnologyMapper technologyMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private ExpertMapper expertMapper;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${image.url}")
    private String url;
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userMapper = userMapper;
    }

    public User register(User userToAdd) {
        final String username = userToAdd.getUsername();
        System.out.println("username1111 "+username);
        if (userMapper.findByUsername(username)!=null || customerMapper.findByTel(userToAdd.getTel())!=null){
            throw  new BusinessException(ResultCode.USER_HAS_EXISTED);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
//        final String regStr = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
//        if (username == null || rawPassword == null){
//            throw new BusinessException(ResultCode. PARAM_IS_BLANK);
//        }
//        if (!rawPassword.matches(regStr)){
//            throw new BusinessException(ResultCode.PASSWORD_IS_INVALID);
//        }
        userToAdd.setUsername(username);
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setRegistertime(new Date());
        int i = userMapper.insert(userToAdd);

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setTel(userToAdd.getTel());
        customerMapper.insert(customer);
        userMapper.insertUserRole(userToAdd.getId(),4);
        return userMapper.findByUsername(username);
    }

    @Override
    public UserDTO login(String loginname, String password) {
        String username = loginname;
        //判断登陆名是username，email或tel
        User customerIsExist = customerMapper.getInfoByUsername(username);
        User technologyIsExist = technologyMapper.getInfoByUsername(username);
        User expertIsExit = expertMapper.getInfoByUsername(username);
        User adminIsExit = adminMapper.getInfoByUsername(username);
        User userIsExit = userMapper.findByUsername(username);
//        User isExist = userMapper.findByLoginname(username);
        Integer id = 0;
        if (customerIsExist!=null){
            username = customerIsExist.getUsername();
            id = customerMapper.getByUsername(username).getId();
        }else if (technologyIsExist!=null){
            username = technologyIsExist.getUsername();
            id = technologyMapper.getByUsername(username).getId();
        }else if (expertIsExit!=null){
            username = expertIsExit.getUsername();
            id = expertMapper.getByUsername(username).getId();
        }else if (adminIsExit!=null){
            username = adminIsExit.getUsername();
            id = adminMapper.getByUsername(username).getId();
        }else if(userIsExit!=null){
            username = userIsExit.getUsername();
            id = userIsExit.getId();
        }else {
            throw new BusinessException(ResultCode.USER_LOGIN_ERROR);
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userMapper.findByUsername(username);
        if (user == null || !encoder.matches(password,user.getPassword())){
            throw new BusinessException(ResultCode.USER_LOGIN_ERROR);
        }
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username,password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
//        User newUser = userMapper.getUserInfo(username);
        userMapper.updateUserLoginTime(new Date(),username);
        Role role = roleMapper.getRoleByUserId(user.getId());
        String roleName = role.getName();
        System.out.println("token:"+token);
        UserDTO userDTO = new UserDTO();

        userDTO.setUser_id(id);
        userDTO.setToken(token);
        userDTO.setUsername(username);
//        userDTO.setPassword(user.getPassword());
        userDTO.setImage(url+user.getImage());
        userDTO.setRegister_time(user.getRegistertime());
        userDTO.setRole(roleName);
        userDTO.setRealname(user.getRealname());
        return userDTO;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token,user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
