package com.jit.robert.mapper;

import com.jit.robert.domain.Pound;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PoundMapper {

    //插入塘口数据
    @Insert("insert into pound(name,username,location,length,width,depth,max_depth,toward," +
            "longitude1,latitude1,longitude2,latitude2,longitude3,latitude3,longitude4,latitude4) " +
            "values(#{pound.name},#{pound.username},#{pound.location},#{pound.length},#{pound.width},#{pound.depth}," +
            "#{pound.max_depth},#{pound.toward},#{pound.longitude1},#{pound.latitude1},#{pound.longitude2}," +
            "#{pound.latitude2},#{pound.longitude3},#{pound.latitude3},#{pound.longitude4},#{pound.latitude4})")
    @Options(useGeneratedKeys = true,keyProperty = "pound.id")
    int insert(@Param("pound")Pound pound);

    //根据id删除塘口
    @Delete("delete from pound where id=#{id}")
    int delete(@Param("id")Integer id);

    //根据id更新塘口
    @Update("update pound set name=#{pound.name},username=#{pound.username},location=#{pound.location},length=#{pound.length}," +
            "width=#{pound.width},depth=#{pound.depth},max_depth=#{pound.max_depth},toward=#{pound.toward}," +
            "longitude1=#{pound.longitude1},latitude1=#{pound.latitude1},longitude2=#{pound.longitude2},latitude2=#{pound.latitude2}," +
            "longitude3=#{pound.longitude3},latitude3=#{pound.latitude3},longitude4=#{pound.longitude4},latitude4=#{pound.latitude4} where id=#{pound.id}")
    int update(@Param("pound")Pound pound);

    // 获取某一用户的所有塘口
    @Select("select * from pound where username=#{username}")
    List<Pound> getAllPoundsByCustomer(@Param("username")String username);

    @Select("select * from pound where id=#{id}")
    Pound getPoundById(@Param("id")Integer id);
}
