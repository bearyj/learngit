package com.jit.robert.mapper;


import com.jit.robert.domain.Feed;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FeedMapper {
    @Insert("insert into feed(username,pound_id,count1,count2,count3,count4,count5,count6,date) values(" +
            "#{feed.username},#{feed.pound_id},#{feed.count1},#{feed.count2},#{feed.count3},#{feed.count4},#{feed.count5},#{feed.count6},#{feed.date})")
    @Options(useGeneratedKeys = true,keyProperty = "feed.id")
    int insert(@Param("feed")Feed feed);

    @Delete("delete from feed where id = #{id}")
    int delete(@Param("id")Integer id);

    @Update("update feed set username=#{feed.username},pound_id=#{feed.pound_id},count1=#{feed.count1},count2=#{feed.count2},count3=#{feed.count3},count4=#{feed.count4},count5=#{feed.count5},count6=#{feed.count6},date=#{feed.date} where id=#{feed.id}")
    int update(@Param("feed")Feed feed);

    @Select("select * from feed where id = #{id} and username = #{username}")
    Feed findByIdAndUsername(@Param("id") Integer id,@Param("username") String username);

    @Select("select * from feed where pound_id = #{pound_id} and date=#{date} ")
    Feed findByDate(@Param("pound_id")Integer pound_id, @Param("date")String date);

    @Select("select * from feed where pound_id=#{pound_id}")
    List<Feed> getFeeds(@Param("pound_id")Integer pound_id);
}
