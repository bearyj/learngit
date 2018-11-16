package com.jit.robert.mapper;

import com.jit.robert.common.DynamicSql;
import com.jit.robert.domain.User;

import com.jit.robert.dto.CustomerDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component(value = "userRepository")
public interface UserMapper {

    @Insert("INSERT INTO user(username, password, registertime, login_time, image) VALUES(#{user.username}, #{user.password},#{user.registertime},#{user.login_time},#{user.image})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")//mybatis使用注解方式插入数据后获取自增长的主键值
    int insert(@Param("user") User user);

    @Select("select u.*, r.name as roles from user u left join user_role ur on u.id = ur.user_id left join role r on r.id = ur.role_id where u.username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("select u.*, r.name as role from user u left join user_role ur on u.id = ur.user_id left join role r on r.id = ur.role_id where u.id = #{id}")
    User getUserById(@Param("id") Integer id);

    @Update("update user set login_time = #{time} where username = #{username}")
    void updateUserLoginTime(@Param("time")Date time, @Param("username")String username);

    @Insert("insert into user_role(user_id,role_id) values(#{user_id},#{role_id})")
    int insertUserRole(@Param("user_id") Integer user_id, @Param("role_id") Integer role_id);

    @Select("SELECT image FROM user WHERE username = #{username}")
    String getUserImage(@Param("username") String username);

    @Update("update user set image = #{image} where username = #{username}")
    void updateUserImage(@Param("username") String username, @Param("image") String image);

    @Select("select * from user where username=#{loginname}")
    User findByLoginname(@Param("loginname")String loginname);

    @DeleteProvider(type = DynamicSql.class, method = "deleteUserBatch")
    void deleteUserBatch(@Param("ids") String ids);

    @Delete("delete from user where id = #{id}")
    void deleteUser(@Param("id") Integer id);

    @Select("select id from user where username = #{username}")
    Integer getUserIdByUsername(@Param("username") String username);

    @Update("update user_role set role_id = #{role_id} where user_id = #{user_id}")
    void updateUserRole(@Param("user_id")Integer  user_id,@Param("role_id")Integer role_id);

    //修改密码
    @Update("update user set password = #{password} where username=#{username}")
    int updatePassword(@Param("password")String password,@Param("username") String username);
	
    @Select("select role_id from user_role where user_id = #{user_id}")
    Integer getRoleIdByUserId(@Param("user_id")Integer  user_id);

    @Select("select u.id,u.username from user u")
    List<CustomerDTO> getAllCustomerDTO();

    @Select("select id,username from customer where username = #{username}")
    CustomerDTO getCustomerDTOByUsername(@Param("username") String username);

}


