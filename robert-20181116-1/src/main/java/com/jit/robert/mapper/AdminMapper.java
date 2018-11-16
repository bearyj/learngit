package com.jit.robert.mapper;

import com.jit.robert.domain.Admin;
import com.jit.robert.domain.User;
import com.jit.robert.dto.AdminDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminMapper {

    @Insert("insert into admin(username, realname, number, department, user_group, status, remark) values(#{admin.username}, #{admin.realname}, #{admin.number}, " +
            "#{admin.department}, #{admin.user_group}, #{admin.status}, #{admin.remark})")
    @Options(useGeneratedKeys = true, keyProperty = "admin.id")
    void insert(@Param("admin")Admin admin);

    @Select("select a.*,u.image as image,u.registertime from admin a left join user u on a.username=u.username where a.username = #{username}")
    Admin getByUsername(@Param("username") String username);

    @Select("select A.*, B.login_time from (select * from admin) AS A LEFT JOIN " +
            "(select username, login_time from user) AS B ON A.username = B.username")
    List<AdminDTO> getAllAdmins();


    @Select("select a.*,u.image as image,u.login_time as login_time from admin a left join user u on a.username=u.username where a.id = #{id}")
    AdminDTO getAdminById(@Param("id")Integer id);

    @Update("update admin set status = #{status} where username = #{username}")
    int updateUserPower(@Param("status") Integer status, @Param("username") String username);

    @Update("update admin set department=#{admin.department}, remark=#{admin.remark} where username=#{admin.username}")
    int updateAdminInfo(@Param("admin")Admin admin);

    @Select("SELECT * FROM user u INNER JOIN admin c ON u.`username` = c.`username` WHERE " +
            "1=1 AND (BINARY c.`username`=#{username})")
    User getInfoByUsername(@Param("username") String username);

}
