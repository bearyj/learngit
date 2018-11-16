package com.jit.robert.mapper;


import com.jit.robert.domain.Water;
import com.jit.robert.dto.DiaryDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WaterMapper {

    @Insert("insert into water(NH,Nano2,Alkali,O2,temperature,pound_id,image,username,remark,date,medicine,weather,ph) values(" +
            "#{water.NH},#{water.Nano2},#{water.Alkali},#{water.O2},#{water.temperature},#{water.pound_id}," +
            "#{water.image},#{water.username},#{water.remark},#{water.date},#{water.medicine},#{water.weather},#{water.ph})")
    @Options(useGeneratedKeys = true,keyProperty = "water.id")
    int insert(@Param("water")Water water);

    @Delete("delete from water where id = #{id}")
    int delete(@Param("id")Integer id);

    @Update("update water set NH=#{water.NH},Nano2=#{water.Nano2},Alkali=#{water.Alkali},O2=#{water.O2}," +
            "temperature=#{water.temperature},pound_id=#{water.pound_id},image=#{water.image}," +
            "username=#{water.username},remark=#{water.remark},date=#{water.date},medicine=#{water.medicine},weather=#{water.weather},ph=#{water.ph} where id=#{water.id}")
    int update(@Param("water")Water water);

    @Select("select * from water where id = #{id} and username = #{username}")
    Water findByIdAndUsername(@Param("id") Integer id,@Param("username") String username);

    @Select("select * from water where date=#{date}")
    Water findByDate(@Param("date")String date);

    @Select("select * from water where pound_id=#{pound_id}")
    List<Water> getWaters(@Param("pound_id")Integer pound_id);

    @Select("SELECT w.*,f.count1,f.count2,f.count3,f.count4,f.count5,f.count6,f.count1+f.count2+f.count3+f.count4+f.count5+f.count6 as count_total FROM robert.water w left join feed f on w.date=f.date where w.pound_id=#{pound_id} and  w.date between #{start_date} and #{end_date} ;")
    List<DiaryDTO> getDiaryByPound(@Param("pound_id")Integer pound_id,@Param("start_date")String start_date,@Param("end_date")String end_date);


}
