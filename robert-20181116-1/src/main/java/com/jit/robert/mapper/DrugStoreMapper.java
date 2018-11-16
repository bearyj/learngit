package com.jit.robert.mapper;

import com.jit.robert.domain.DrugStore;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DrugStoreMapper {

    @Insert("insert into drug_store(name,kind,subKind,type,price,manualInstruct,company,contact,telPhone,image,visitCount,publishTime) values(#{drug_store.name},#{drug_store.kind},#{drug_store.subKind},#{drug_store.type},#{drug_store.price},#{drug_store.manualInstruct},#{drug_store.company},#{drug_store.contact},#{drug_store.telPhone},#{drug_store.image},#{drug_store.visitCount},#{drug_store.publishTime})")
    @Options(useGeneratedKeys = true,keyProperty = "drug_store.id")
    int insert(@Param("drug_store")DrugStore drug_store);

    @Delete("delete from drug_store where id=#{id}")
    int delete(@Param("id")Integer id);

    @Update("update drug_store set name=#{drug_store.name},kind=#{drug_store.kind},subKind=#{drug_store.subKind},type=#{drug_store.type},price=#{drug_store.price},manualInstruct=#{drug_store.manualInstruct},company=#{drug_store.company},contact=#{drug_store.contact},telPhone=#{drug_store.telPhone},image=#{drug_store.image},visitCount=#{drug_store.visitCount},publishTime=#{drug_store.publishTime} where id=#{drug_store.id}")
    int update(@Param("drug_store")DrugStore drug_store);

    @Select("select * from drug_store where id=#{id}")
    DrugStore getOneDrug(@Param("id")Integer id);

    @Select("select * from drug_store order by publishTime desc")
    List<DrugStore> getAllDrugs();

    @Select("select * from drug_store where subKind=#{subKind} order by publishTime desc")
    List<DrugStore> getAllDrugsBysubkind(@Param("subKind")String subKind);

    @Update("update drug_store set visitCount = #{count} where id = #{id}")
    void updateVisitCount(@Param("count") Integer count,@Param("id") Integer id);

    @Select("SELECT distinct subKind FROM drug_store")
    List<String> getAllTypes();
}
