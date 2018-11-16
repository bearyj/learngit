package com.jit.robert.mapper;

import com.jit.robert.common.DynamicSql;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Expert;
import com.jit.robert.domain.Technology;
import com.jit.robert.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExpertMapper {

    @Insert("insert into expert(product, major, sign_time, number, degree, company, remark, username, tel, email, sex, age, province, city, county, address, realname ) values (#{expert.product}, #{expert.major}, #{expert.sign_time}," +
            "#{expert.number}, #{expert.degree}, #{expert.company}, #{expert.remark}, #{expert.username}, #{expert.tel}, #{expert.email}, #{expert.sex}, #{expert.age}, " +
            "#{expert.province}, #{expert.city}, #{expert.county}, #{expert.address}, #{expert.realname})")
    @Options(useGeneratedKeys = true, keyProperty = "expert.id")
    void insert(@Param("expert") Expert expert);

    @Select("select e.*,u.image from expert e left join user u on e.username=u.username  where e.username = #{username}")
    Expert getByUsername(@Param("username") String username);

    @Select("select e.*,u.image from expert e left join user u on e.username=u.username order by e.sign_time desc")
    List<Expert> getAllExperts();

    @SelectProvider(type = DynamicSql.class, method = "QuerySql")
    List<Expert> getExpertsByStrategy(@Param("type")Integer type, @Param("strategy")QueryStrategy strategy);

    @Select("select e.*,u.image as image from expert e left join user u on e.username=u.username where e.username=#{username}")
    Expert getExpertByUsername(@Param("username")String username);

    @Select("select e.*,u.image as image from expert e left join user u on e.username=u.username where e.id=#{id}")
    Expert getExpertById(@Param("id")Integer id);


    @Update("update expert set product = #{currentExpert.product}, major = #{currentExpert.major}, degree = #{currentExpert.degree}, company = #{currentExpert.company}, remark = #{currentExpert.remark}, tel = #{currentExpert.tel}, " +
            "email = #{currentExpert.email}, age = #{currentExpert.age}, province = #{currentExpert.province}, city = #{currentExpert.city}, county = #{currentExpert.county}, address = #{currentExpert.address}," +
            "realname = #{currentExpert.realname} where id = #{currentExpert.id}")
    void updateExpert(@Param("currentExpert")Expert currentExpert);

    @SelectProvider(type = DynamicSql.class, method = "getUserIds")
    List<Integer> getUserIds(@Param("type") Integer type, @Param("ids") String ids);

    @Select("select id as userId from user where username = (select username from expert where id = #{id})")
    Integer getUserId(@Param("id") Integer id);

    @Select("SELECT * FROM user u INNER JOIN expert c ON u.`username` = c.`username` WHERE " +
            "1=1 AND (BINARY c.`username`=#{username} OR c.`email`=#{username} OR c.`tel`=#{username})")
    User getInfoByUsername(@Param("username") String username);
}
