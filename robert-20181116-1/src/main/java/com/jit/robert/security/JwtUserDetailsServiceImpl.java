package com.jit.robert.security;

import com.jit.robert.domain.Permission;
import com.jit.robert.domain.Role;
import com.jit.robert.mapper.PermissionMapper;
import com.jit.robert.mapper.RoleMapper;
import com.jit.robert.mapper.UserMapper;
import com.jit.robert.domain.User;
import com.jit.robert.rbac.MyGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        System.out.println("user.name: " + user.getUsername() + " user: " + user);
        if (user != null) {
//            Collection<GrantedAuthority> grantedAuthorities = obtionGrantedAuthorities(user);

            List<Permission> permissions = permissionMapper.findByUserId(user.getId());
            System.out.println("permissions: " + permissions);
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Permission permission : permissions) {
                if (permission != null && permission.getName() != null) {
                    GrantedAuthority grantedAuthority = new MyGrantedAuthority(permission.getUrl(),permission.getMethod());
                    grantedAuthorities.add(grantedAuthority);
                    System.out.println("grantedAuthorities:" + grantedAuthorities.toString());
                }
            }
            return new JwtUser(user.getId(),user.getUsername(),user.getPassword(),grantedAuthorities,user.getRegistertime());
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
    }
    private Set<GrantedAuthority> obtionGrantedAuthorities( User user){
        List<Role> roles=roleMapper.findRolesByUsername(user.getUsername());
        Set<GrantedAuthority>authSet = new HashSet<GrantedAuthority>();
        for (Role role : roles) {
            // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
            authSet.add(new SimpleGrantedAuthority(role.getName()));
        }
        System.out.println("authSet11 "+ authSet);
        return authSet;
    }
}