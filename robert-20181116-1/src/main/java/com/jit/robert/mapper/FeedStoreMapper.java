package com.jit.robert.mapper;

import com.jit.robert.domain.FeedStore;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FeedStoreMapper {

    @Insert("insert into feed_store(name, kind, subKind, type, price, manualInstruct, company, contact, telPhone, image, visitCount, publishTime) values(#{feedStore.name}, " +
            "#{feedStore.kind}, #{feedStore.subKind}, #{feedStore.type}, #{feedStore.price}, #{feedStore.manualInstruct}, #{feedStore.company}, #{feedStore.contact}, #{feedStore.telPhone}, " +
            "#{feedStore.image}, #{feedStore.visitCount}, #{feedStore.publishTime})")
    @Options(useGeneratedKeys = true, keyProperty = "feedStore.id")
    int insert(@Param("feedStore")FeedStore feedStore);

    @Select("select * from feed_store where id = #{id}")
    FeedStore getFeedStoreById(@Param("id") Integer id);

    @Delete("delete from feed_store where id = #{id}")
    int delete(@Param("id") Integer id);

    @Update("update feed_store set name = #{feedStore.name}, kind = #{feedStore.kind}, subKind = #{feedStore.subKind}, type = #{feedStore.type}, price = #{feedStore.price}, manualInstruct = #{feedStore.manualInstruct}," +
            "company = #{feedStore.company}, contact = #{feedStore.contact}, telPhone = #{feedStore.telPhone}, image = #{feedStore.image}, visitCount = #{feedStore.visitCount}, publishTime = #{feedStore.publishTime} where id = #{id}")
    int update( @Param("id")Integer id, @Param("feedStore")FeedStore feedStore);

    @Select("select * from feed_store order by publishTime desc")
    List<FeedStore> getAllFeedStores();

    @Select("select * from feed_store where subKind = #{subKind} order by publishTime desc")
    List<FeedStore> getFeedStoresBySubKind(@Param("subKind") String subKind);

    @Update("update feed_store set visitCount = #{count} where id = #{id}")
    void updateVisitCount(@Param("count") Integer count,@Param("id") Integer id);

    @Select("SELECT distinct subKind FROM feed_store")
    List<String> getAllTypes();
}
