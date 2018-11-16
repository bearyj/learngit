package com.jit.robert.mapper;

import com.jit.robert.common.DynamicSql;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Customer;
import com.jit.robert.domain.Technology;
import com.jit.robert.domain.User;
import com.jit.robert.dto.TechnologyDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TechnologyMapper {

    @Insert("insert into technology(number, position, enter_time, username, tel, email, realname, sex, age, province, city, county, address) values(#{technology.number}, #{technology.position}, " +
            "#{technology.enter_time}, #{technology.username}, #{technology.tel}, #{technology.email}, #{technology.realname}, #{technology.sex}, #{technology.age}," +
            "#{technology.province}, #{technology.city}, #{technology.county}, #{technology.address})")
    @Options(useGeneratedKeys = true, keyProperty = "technology.id")
    void insert(@Param("technology")Technology technology);

    @Select("select A.*, B.repairNum from (select * from technology where username = #{username}) AS A LEFT JOIN " +
            "(select technology_id, COUNT(technology_id) AS repairNum FROM robert.`repair` GROUP BY technology_id) AS B ON A.id = B.technology_id")
    TechnologyDTO getByUsername(@Param("username") String username);
    @Select("select A.*, B.repairNum from (select t.*,u.image from technology t left join user u on t.username=u.username where t.id = #{id}) AS A LEFT JOIN " +
            "(select technology_id, COUNT(technology_id) AS repairNum FROM robert.`repair` GROUP BY technology_id) AS B ON A.id = B.technology_id")
    TechnologyDTO getTechnologyDTOById(@Param("id")Integer id);

    @Select("select A.*, B.repairNum from (select * from technology order by enter_time desc) AS A LEFT JOIN " +
            "(select technology_id, COUNT(technology_id) AS repairNum FROM robert.`repair` GROUP BY technology_id) AS B ON A.id = B.technology_id")
    List<TechnologyDTO> getAllTechnologies();

    @SelectProvider(type = DynamicSql.class, method = "QuerySql")
    List<TechnologyDTO> getTechnologiesByStrategy(@Param("type") Integer type, @Param("strategy") QueryStrategy strategy);

    @Select("select * from technology where id = #{id}")
    Technology getTechnologyById(@Param("id") Integer id);

    @Update("update technology set realname = #{technology.realname}, sex = #{technology.sex}, position = #{technology.position}, tel = #{technology.tel}, email = #{technology.email}, age = #{technology.age}, province = #{technology.province}," +
            "city = #{technology.city}, county = #{technology.county}, address = #{technology.address} where id = #{technology.id}")
    void updateTechnology(@Param("technology") Technology technology);

    @SelectProvider(type = DynamicSql.class, method = "getUserIds")
    List<Integer> getUserIds(@Param("type") Integer type, @Param("ids") String ids);

    @Select("select id as userId from user where username = (select username from technology where id = #{id})")
    Integer getUserId(@Param("id") Integer id);

    @Select("select id from technology where username = #{username}")
    Integer getTechnologyId(@Param("username") String username);

    @Select("SELECT * FROM user u INNER JOIN technology c ON u.`username` = c.`username` WHERE " +
            "1=1 AND (BINARY c.`username`=#{username} OR c.`email`=#{username} OR c.`tel`=#{username})")
    User getInfoByUsername(@Param("username") String username);
}
