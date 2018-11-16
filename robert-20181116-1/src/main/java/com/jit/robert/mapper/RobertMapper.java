package com.jit.robert.mapper;

import com.jit.robert.domain.Robert;
import com.jit.robert.dto.RobertDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RobertMapper {
    //创建机器人
    @Insert("insert into robert(number,user_id,type) values(#{robert.number},#{robert.user_id},#{robert.type})")
    @Options(useGeneratedKeys = true,keyProperty = "robert.id")
    int insert(@Param("robert")Robert robert);

    //管理员为客户分配机器人
    @Update("update robert set user_id=#{robert.user_id} where id=#{robert.id}")
    int assignRobertByAdmin(@Param("robert")Robert robert);

    //删除机器人
    @Delete("delete from robert where id=#{id}")
    int deleteRobert(@Param("id")Integer id);

    //修改机器人数据
    @Update("update robert set number=#{robert.number},type=#{robert.type} where id=#{robert.id}")
    int updateRobert(@Param("robert")Robert robert);

    // 根据id获取一条机器人信息
    @Select("select * from robert where id=#{id}")
    Robert getRobertById(@Param("id")Integer id);

    @Select("SELECT r.*,c.username,c.realname,c.sex,c.age,c.tel,c.province,c.city,c.county,c.address,c.type as category,s.feed_name,s.medicine_name,u.registertime FROM robert r left join user u on r.user_id=u.id left join customer c on u.username=c.username left join settings s on r.id=s.robert_id where r.id=#{id}")
    RobertDTO getRobertInfoById(@Param("id")Integer id);

    //根据number获取机器人
    @Select("select * from robert where number=#{number}")
    Robert getRobertByNumber(@Param("number")String number);

    //根据客户id获取客户的所有机器人
    @Select("SELECT r.*,c.id AS customer_id, c.username,c.realname,c.sex,c.age,c.tel,c.province,c.city,c.county,c.address,c.type as category,s.feed_name,s.medicine_name,u.registertime FROM robert r left join user u on r.user_id=u.id left join customer c on u.username=c.username left join settings s on r.id=s.robert_id where r.user_id=#{customer_id} ORDER BY registertime DESC")
    List<RobertDTO> getAllRobertsByCustemer(@Param("customer_id")Integer customer_id);

    //根据类型获取所有机器人
    @Select("SELECT r.*,c.id AS customer_id,c.username,c.realname,c.sex,c.age,c.tel,c.province,c.city,c.county,c.address,c.type as category,s.feed_name,s.medicine_name,u.registertime FROM robert r left join user u on r.user_id=u.id left join customer c on u.username=c.username left join settings s on r.id=s.robert_id where r.type=#{type} ORDER BY registertime DESC")
    List<RobertDTO> getAllRobertsByType(@Param("type")String type);

    //获取所有机器人
    @Select("SELECT r.*,c.id AS customer_id,c.username,c.realname,c.sex,c.age,c.tel,c.province,c.city,c.county,c.address,c.type as category,s.feed_name,s.medicine_name,u.registertime FROM robert r left join user u on r.user_id=u.id  left join customer c on u.username=c.username left join settings s on r.id=s.robert_id ORDER BY registertime DESC")
    List<RobertDTO> getAllRoberts();

    @Select("select id from robert where user_id = #{user_id}")
    List<Integer> getRobertIdsByCustomer(@Param("user_id")Integer user_id);

    @Select("SELECT distinct type FROM robert")
    List<String> getAllRobertType();

    @Select("select * from robert")
    List<Robert> getAllRobertsWithoutPage();

}
