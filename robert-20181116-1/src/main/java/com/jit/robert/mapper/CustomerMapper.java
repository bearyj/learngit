package com.jit.robert.mapper;

import com.jit.robert.common.DynamicSql;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Customer;
import com.jit.robert.domain.Expert;
import com.jit.robert.domain.Robert;
import com.jit.robert.domain.User;
import com.jit.robert.dto.CustomerDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Mapper
public interface CustomerMapper {

    @Insert("insert into customer(username, type, tel, email, realname, sex, age, province, city, county, address) values(#{customer.username}, #{customer.type}, #{customer.tel}," +
            "#{customer.email}, #{customer.realname}, #{customer.sex}, #{customer.age}, #{customer.province}, #{customer.city},#{customer.county}, #{customer.address})")
    @Options(useGeneratedKeys = true, keyProperty = "customer.id")
    void insert(@Param("customer")Customer customer);

    @Select("select * from customer where username = #{username} or tel = #{username} or email= #{username}")
    Customer getByUsername(@Param("username") String username);

    @Select("select * from customer")
    List<Customer> getAllCustomers();

    @SelectProvider(type = DynamicSql.class, method = "QuerySql")
    List<Customer> getCustomersByStrategy(@Param("type")Integer type, @Param("strategy")QueryStrategy strategy);

    @Select("select * from customer where id = #{id}")
    Customer getByCustomerId(@Param("id") Integer id);

    @Update("update customer set realname = #{customer.realname}, sex = #{customer.sex}, type = #{customer.type}, tel = #{customer.tel}, email = #{customer.email}, age = #{customer.age}, province = #{customer.province}, " +
            "city = #{customer.city}, county = #{customer.county}, address = #{customer.address} where id = #{customer.id}")
    void updateCustomer(@Param("customer") Customer customer);

    @SelectProvider(type = DynamicSql.class, method = "getUserIds")
    List<Integer> getUserIds(@Param("type") Integer type, @Param("ids") String ids);

    @Select("select id as userId from user where username = (select username from customer where id = #{id})")
    Integer getUserId(@Param("id") Integer id);

    @Select("select * from robert where user_id = #{customer_id}")
    List<Robert> getRobertsByCustomerId(@Param("customer_id")Integer customer_id);

    @Select("select * from customer where tel = #{tel}")
    Customer findByTel(@Param("tel")String tel);

    @Select("SELECT * FROM user u INNER JOIN customer c ON u.`username` = c.`username` WHERE " +
            "1=1 AND (BINARY c.`username`=#{username} OR c.`email`=#{username} OR c.`tel`=#{username})")
    User getInfoByUsername(@Param("username") String username);
}
