package com.jit.robert.mapper;

import com.jit.robert.common.DynamicSql;
import com.jit.robert.domain.Repair;
import com.jit.robert.dto.RepairDTO;
import com.jit.robert.dto.TaskDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RepairMapper {

    @Insert("insert into repair(technology_id, robert_id, description, status, time) values (#{repair.technology_id}, #{repair.robert_id}," +
            "#{repair.description}, #{repair.status}, #{repair.time})")
    @Options(useGeneratedKeys = true,keyProperty = "repair.id")
    void insert(@Param("repair") Repair repair);

    @Select("SELECT E.*, F.username, F.realname, F.sex ,F.age ,F.tel FROM " +
            "(SELECT C.*,D.username FROM " +
            "(SELECT A.*, B.number ,B.user_id, B.type FROM (SELECT * FROM robert.repair WHERE technology_id =#{technology_id}) AS A LEFT JOIN  " +
            "(SELECT r.number, r.id, r.user_id, r.type FROM robert r) AS B ON B.id = A.robert_id)AS C LEFT JOIN " +
            "(SELECT u.id, u.username FROM user u)AS D ON D.id = C.user_id) AS E LEFT JOIN " +
            "(SELECT c.username, c.realname, c.sex, c.age, c.tel FROM customer c)AS F ON F.username = E.username ORDER BY time DESC")
    List<TaskDTO> getTaskByTechnologyId(@Param("technology_id") Integer technology_id);

    @Select("select * from robert.repair where id = #{id}")
    Repair getRepairById(@Param("id") Integer id);

    @Update("update repair set status = #{repair.status} where id = #{id}")
    int updateRepairStatus(@Param("id") Integer id,@Param("repair") Repair repair);

    @Select("select * from repair where robert_id = #{robert_id} order by time desc limit 1 ")
    Repair getRepairByRobertId(@Param("robert_id") Integer robert_id);

    @Update("update repair set description = #{repair.description}, time = #{repair.time} where id = #{repair.id}")
    void updateRepair(@Param("repair") Repair repair);

    @SelectProvider(type = DynamicSql.class, method = "getRepairListSql")
    List<Repair> getRepairListByRobertId(@Param("status") Integer status, @Param("robert_id")Integer robert_id);

    @Update("update repair set technology_id = #{technology_id}, status = 1 where id = #{id}")
    void updateRepairTechnology(@Param("id") Integer id,@Param("technology_id")Integer technology_id);

    @Select("select E.*, F.realname, F.sex,F.age,F.tel from " +
            "(select C.*,D.username from " +
            "(select A.*, B.number ,B.user_id, B.type from (select * from robert.repair where id = #{id}) AS A LEFT JOIN " +
            "(select r.number, r.id, r.user_id, r.type from robert r) AS B ON B.id = A.robert_id)AS C LEFT JOIN " +
            "(select u.id, u.username from user u) as D on D.id = C.user_id)as E left join " +
            "(select c.username, c.realname, c.sex, c.age,c.tel from customer c) AS F ON F.username = E.username ORDER BY time DESC")
    TaskDTO getTaskByRepairId(@Param("id") Integer id);

    @Select("select E.*,F.realname, F.sex,F.age,F.tel from " +
            "(select C.*,D.username from " +
            "(select A.*, B.number ,B.user_id, B.type from (select * from robert.repair) AS A LEFT JOIN " +
            "(select r.number, r.id, r.user_id, r.type from robert r) AS B ON B.id = A.robert_id)AS C LEFT JOIN " +
            "(select u.id, u.username from user u) as D on D.id = C.user_id)as E left join " +
            "(select c.username, c.realname, c.sex, c.age,c.tel from customer c) AS F ON F.username = E.username ORDER BY time DESC")
    List<TaskDTO> getAllRepairTasks();

    @Select("select E.*,F.realname, F.sex,F.age,F.tel from " +
            "(select C.*,D.username from " +
            "(select A.*, B.number ,B.user_id, B.type from (select * from robert.repair where status = #{status}) AS A LEFT JOIN " +
            "(select r.number, r.id, r.user_id, r.type from robert r) AS B ON B.id = A.robert_id)AS C LEFT JOIN " +
            "(select u.id, u.username from user u) as D on D.id = C.user_id)as E left join " +
            "(select c.username,c.realname, c.sex, c.age,c.tel from customer c) AS F ON F.username = E.username order by time desc")
    List<TaskDTO> getRepairTasksByStatus(@Param("status") Integer status);

    @SelectProvider(type = DynamicSql.class, method = "getRepairDTOListSql")
    List<RepairDTO> getRepairByTechnologyId(@Param("status") Integer status, @Param("robert_id") Integer robert_id);
}
