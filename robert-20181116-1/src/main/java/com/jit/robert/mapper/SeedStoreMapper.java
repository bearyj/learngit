package com.jit.robert.mapper;

import com.jit.robert.common.DynamicSql;
import com.jit.robert.domain.FeedStore;
import com.jit.robert.domain.SeedStore;
import org.apache.ibatis.annotations.*;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SeedStoreMapper {

    @Insert("insert into seed_store(title, kind, subKind, price, description, company, productPlace, contact, telPhone, image, visitCount, publishTime) values(#{seedStore.title}, #{seedStore.kind}, #{seedStore.subKind}," +
            "#{seedStore.price}, #{seedStore.description}, #{seedStore.company}, #{seedStore.productPlace},#{seedStore.contact}, #{seedStore.telPhone}, #{seedStore.image}, #{seedStore.visitCount}, #{seedStore.publishTime})")
    @Options(useGeneratedKeys = true, keyProperty = "seedStore.id")
    int insert(@Param("seedStore")SeedStore seedStore);

    @Select("select * from seed_store where id = #{id}")
    SeedStore getSeedStoreById(@Param("id")Integer id);

    @Delete("delete from seed_store where id = #{id}")
    int delete(@Param("id")Integer id);

    @Update("update seed_store set title = #{seedStore.title}, kind = #{seedStore.kind}, subKind = #{seedStore.subKind}, price = #{seedStore.price}, description = #{seedStore.description}," +
            "company = #{seedStore.company}, productPlace = #{seedStore.productPlace}, contact = #{seedStore.contact}, telPhone = #{seedStore.telPhone}, image = #{seedStore.image}, visitCount = #{seedStore.visitCount}," +
            "publishTime = #{seedStore.publishTime} where id = #{seedStore.id}")
    int update(@Param("seedStore")SeedStore seedStore);

    @Select("select * from seed_store order by publishTime desc")
    List<SeedStore> getAllSeedStores();

    @Select("select * from seed_store where subKind = #{subKind} order by publishTime desc")
    List<SeedStore> getSeedStoresBySubKind(@Param("subKind") String subKind);

    @Update("update seed_store set visitCount = #{count} where id = #{id}")
    void updateVisitCount(@Param("count")Integer count, @Param("id")Integer id);

    @Select("SELECT distinct subKind FROM seed_store")
    List<String> getAllTypes();
}
