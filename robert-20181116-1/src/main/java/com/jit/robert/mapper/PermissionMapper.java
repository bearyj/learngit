package com.jit.robert.mapper;

import com.jit.robert.domain.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PermissionMapper {

    @Select("select * from permission")
    List<Permission> findAll();

    @Select("SELECT p.*, r.`name` AS role FROM user u \n" +
            "LEFT JOIN user_role ur ON u.id= ur.user_id\n" +
            "LEFT JOIN role r ON ur.role_id=r.id\n" +
            "LEFT JOIN role_permission pr ON pr.role_id=r.id\n" +
            "LEFT JOIN permission p ON p.id =pr.permission_id\n" +
            "WHERE u.id = #{user_id}")
    List<Permission> findByUserId(@Param("user_id") Integer user_id);

    @Select("SELECT p.* FROM role r,permission p,role_permission rp WHERE r.id=rp.role_id AND p.id=rp.permission_id AND r.id= #{role_id}")
    List<Permission> findAllPerminssionsByRoleId(@Param("role_id") Integer role_id);
}
