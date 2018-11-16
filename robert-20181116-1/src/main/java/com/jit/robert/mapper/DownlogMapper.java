package com.jit.robert.mapper;


import com.jit.robert.common.DynamicSql;
import com.jit.robert.domain.Downlog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DownlogMapper {

    @Insert("insert into downlog(downlogname,downlogtime,username) values(#{downlog.downlogname},#{downlog.downlogtime},#{downlog.username})")
    int insert(@Param("downlog") Downlog downlog);

    @Update("update downlog set downlogname=#{downlog.downlogname},downlogtime=#{downlog.downlogtime} where id=#{downlog.id}")
    int update(@Param("downlog") Downlog downlog);

    @DeleteProvider(type = DynamicSql.class, method = "deleteDownlogBatchSql")
    int deleteDownlogBatch(@Param("ids") String ids);

    @Delete("delete from downlog where id=#{id}")
    int deleteDownlog(@Param("id") Integer id);

    @Select("select * from downlog order by downlogtime desc")
    List<Downlog> getAllDownlog();

    @Select("select * from downlog where downlogname=#{downlogname} and username=#{username}")
    Downlog getDownlogByName(@Param("downlogname") String downlogname, @Param("username") String username);
}
