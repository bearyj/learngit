package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Product;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Api(value = "product",description = "百科---产品库")
@ResponseResult
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    /**
     * 增加百科
     * @return
     */
    @ApiOperation(value = "增加百科",notes = "增加百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")})
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Product addProduct(MultipartFile image, HttpServletRequest request) throws IOException {
        return productService.insertProduct(image,request);
    }


    /**
     * 删除百科
     * @param id
     * @return
     */
    @ApiOperation(value = "删除百科",notes = "删除百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "百科对象id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Boolean deleteProduct(@PathVariable Integer id){
        return productService.deleteProduct(id);
    }


    /**
     * 修改百科
     * @param id
     * @return
     */
    @ApiOperation(value = "修改百科",notes = "修改百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "百科对象id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Product updateProduct(@PathVariable Integer id,MultipartFile image,HttpServletRequest request) throws IOException {
        return productService.updateProduct( id,image,request);
    }


    /**
     * 获取一条百科详情
     * @param id
     * @return
     */
    @ApiOperation(value = "获取一条百科详情",notes = "获取一条百科详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "百科对象id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Product getOneProduct(@PathVariable Integer id){
        return productService.getProductInfo(id);
    }


    /**
     * 获取所有百科
     * @return
     */
    @ApiOperation(value = "获取所有百科",notes = "获取所有百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<Product> getAllProducts(PageQO pageQO){
        return productService.getAllProducts(pageQO);
    }


    /**
     * 根据类别获取百科
     * @param subKind
     * @return
     */
    @ApiOperation(value = "根据类别获取百科",notes = "根据类别获取百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "subKind", value = "百科对象类别（虾类、蟹类、鱼类或所有）", required = true, dataType = "string")
    })
    @RequestMapping(value = "/subKind",method = RequestMethod.GET)
    public PageVO<Product> getProductsBySubkind(@RequestParam("subKind")String subKind, PageQO pageQO){
        return productService.getProductsBySubkind(subKind,pageQO);
    }
}
