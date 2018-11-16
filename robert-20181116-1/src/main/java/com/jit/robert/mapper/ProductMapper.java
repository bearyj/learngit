package com.jit.robert.mapper;

import com.jit.robert.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    @Insert("insert into product(name,kind,subKind,description,image) values(#{product.name},#{product.kind},#{product.subKind},#{product.description},#{product.image})")
    @Options(useGeneratedKeys = true,keyProperty = "product.id")
    int insert(@Param("product")Product product);

    @Delete("delete from product where id=#{id}")
    int delete(@Param("id")Integer id);

    @Update("update product set name=#{product.name},kind=#{product.kind},subKind=#{product.subKind},description=#{product.description},image=#{product.image} where id=#{product.id}")
    int update(@Param("product")Product product);

    @Select("select * from product where id=#{id}")
    Product getOneProduct(@Param("id")Integer id);

    @Select("select * from product")
    List<Product> getAllProducts();

    @Select("select * from product where subKind=#{subKind}")
    List<Product> getProductsBySubkind(@Param("subKind")String subKind);


    @Select("SELECT distinct subKind FROM product")
    List<String> getAllTypes();
}
