package com.jit.robert.mapper;

import com.jit.robert.domain.Disease;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DiseaseMapper {
    @Insert("insert into disease(subKind,diseaseName,cause,symptom,treatment,image,visitCount,publishTime) values(#{disease.subKind},#{disease.diseaseName},#{disease.cause},#{disease.symptom},#{disease.treatment},#{disease.image},#{disease.visitCount},#{disease.publishTime})")
    @Options(useGeneratedKeys = true,keyProperty = "disease.id")
    int insert(@Param("disease") Disease disease);

    @Delete("delete from disease where id=#{id}")
    int delete(@Param("id")Integer id);

    @Update("update disease set subKind=#{disease.subKind},diseaseName=#{disease.diseaseName},cause=#{disease.cause},symptom=#{disease.symptom},treatment=#{disease.treatment},image=#{disease.image},visitCount=#{disease.visitCount},publishTime=#{disease.publishTime} where id=#{disease.id}")
    int update(@Param("disease")Disease disease);

    @Select("select * from disease where id=#{id}")
    Disease getOneDisease(@Param("id")Integer id);

    @Select("select * from disease order by publishTime desc")
    List<Disease> getAllDiseases();

    @Select("select * from disease where subKind=#{subKind} order by publishTime desc")
    List<Disease> getDiseasesBySubkind(@Param("subKind")String subKind);

    @Update("update disease set visitCount = #{count} where id = #{id}")
    void updateVisitCount(@Param("count") Integer count,@Param("id") Integer id);

    @Select("SELECT distinct subKind FROM disease")
    List<String> getAllTypes();
}
