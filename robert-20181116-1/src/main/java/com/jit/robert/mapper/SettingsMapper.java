package com.jit.robert.mapper;

import com.jit.robert.domain.Settings;
import com.jit.robert.dto.SettingsDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SettingsMapper {
    //增加配置
    @Insert("insert into settings(pound_id,robert_id,camera_depth,sensor_depth,cruise_velocity,circle,return_velocity,bird_status,feed_name,feed_weight,feed_velocity,medicine_name,medicine_weight,medicine_velocity,type,category) values(#{settings.pound_id},#{settings.robert_id},#{settings.camera_depth},#{settings.sensor_depth},#{settings.cruise_velocity},#{settings.circle},#{settings.return_velocity},#{settings.bird_status},#{settings.feed_name},#{settings.feed_weight},#{settings.feed_velocity},#{settings.medicine_name},#{settings.medicine_weight},#{settings.medicine_velocity},#{settings.type},#{settings.category})")
    @Options(useGeneratedKeys = true,keyProperty = "settings.id")
    int insert(@Param("settings")Settings settings);

    //更新配置
    @Update("update settings set pound_id=#{settings.pound_id},robert_id=#{settings.robert_id},camera_depth=#{settings.camera_depth},sensor_depth=#{settings.sensor_depth},cruise_velocity=#{settings.cruise_velocity},circle=#{settings.circle},return_velocity=#{settings.return_velocity},bird_status=#{settings.bird_status},feed_name=#{settings.feed_name},feed_weight=#{settings.feed_weight},feed_velocity=#{settings.feed_velocity},medicine_name=#{settings.medicine_name},medicine_weight=#{settings.medicine_weight},medicine_velocity=#{settings.medicine_velocity},type=#{settings.type},category = #{settings.category} where id=#{settings.id}")
    int update(@Param("settings")Settings settings);

    //删除配置
    @Delete("delete from settings where id=#{id}")
    int delete(@Param("id")Integer id);

    //根据塘口获取配置
    @Select("select s.*,p.name as pound_name,r.number as robert_name from settings s left join pound p on s.pound_id=p.id left join robert r on s.robert_id=r.id where s.pound_id=#{pound_id}")
    SettingsDTO getSettingsByPound(@Param("pound_id")Integer pound_id);

    //根据机器人获取配置
    @Select("select s.*,p.name as pound_name,r.number as robert_name from settings s left join pound p on s.pound_id=p.id left join robert r on s.robert_id=r.id where s.robert_id=#{robertId}")
    SettingsDTO getSettingsByRobert(@Param("robertId")Integer robertId);

    //根据id获取配置
    @Select("select s.*,p.name as pound_name,r.number as robert_name from settings s left join pound p on s.pound_id=p.id left join robert r on s.robert_id=r.id where s.id=#{id}")
    SettingsDTO getSettingsById(@Param("id")Integer id);
}
