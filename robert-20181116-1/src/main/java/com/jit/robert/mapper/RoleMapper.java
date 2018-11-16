package com.jit.robert.mapper;

import com.jit.robert.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@Mapper
public interface RoleMapper {

    @Select("SELECT r.name FROM USER u LEFT JOIN user_role ur ON u.id = ur.user_id LEFT JOIN role r ON r.id = ur.role_id WHERE username = #{username}")
    List<Role> findRolesByUsername(@Param("username") String username);

    @Select("select * from role")
    List<Role> findAllRoles();

    @Select("select r.* from role r where id = (  select role_id from user_role where user_id = #{user_id})")
    Role getRoleByUserId(@Param("user_id")Integer user_id);

    @Select("select r.name from role r where id =(select role_id from user_role where user_id = (select id from user where username=#{username}))")
    String getRoleByUsername(@Param("username")String username);
}
