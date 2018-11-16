package com.jit.robert.mapper;

import com.jit.robert.domain.Alarm;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Mapper
public interface AlarmMapper {

    //增加告警信息
    @Insert("insert into alarm(robert_id,oxygen_max,oxygen_min,ph_max,ph_min,temperature_max,temperature_min,power,feed,medicine,wash) values(#{alarm.robert_id},#{alarm.oxygen_max},#{alarm.oxygen_min},#{alarm.ph_max},#{alarm.ph_min},#{alarm.temperature_max},#{alarm.temperature_min},#{alarm.power},#{alarm.feed},#{alarm.medicine},#{alarm.wash})")
    @Options(useGeneratedKeys = true,keyProperty = "alarm.id")
    int insert(@Param("alarm")Alarm alarm);

    //更新告警信息
    @Update("update alarm set robert_id=#{alarm.robert_id},oxygen_max=#{alarm.oxygen_max},oxygen_min=#{alarm.oxygen_min},ph_max=#{alarm.ph_max},ph_min=#{alarm.ph_min},temperature_max=#{alarm.temperature_max},temperature_min=#{alarm.temperature_min},power=#{alarm.power},feed=#{alarm.feed},medicine=#{alarm.medicine},wash=#{alarm.wash} where id=#{alarm.id}")
    int update(@Param("alarm")Alarm alarm);

    //删除告警信息
    @Delete("delete from alarm where id=#{id}")
    int delete(@Param("id")Integer id);

    //获取机器人的预警信息
    @Select("select a.*,r.number as robert_number from alarm a left join robert r on a.robert_id=r.id where robert_id=#{robert_id}")
    Alarm getAlarmByRobert(@Param("robert_id")Integer id);

    @Select("select a.*,r.number as robert_number from alarm a left join robert r on a.robert_id=r.id")
    List<Alarm> getAllAlarm();
}
