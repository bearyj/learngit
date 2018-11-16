package com.jit.robert.controller;

import com.jit.robert.domain.Pound;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.PoundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "pound", description = "塘口管理")
@RestController
@ResponseResult
@RequestMapping("/pound")
public class PoundController {

    @Autowired
    private PoundService poundService;


    /**
     * 增加塘口
     * @param pound
     * @return
     */
    @ApiOperation(value = "增加塘口",notes = "客户增加塘口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pound", value = "塘口对象", required = true, dataType = "Pound")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Pound insertPound(@RequestBody Pound pound){
        return poundService.insertPound(pound);
    }


    /**
     * 删除塘口
     * @param id
     * @return
     */
    @ApiOperation(value = "删除塘口",notes = "客户根据id删除塘口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "塘口id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Boolean deletePound(@PathVariable  Integer id){
        return poundService.deletePoundById(id);
    }


    /**
     * 更新塘口
     * @param id 塘口id
     * @param pound 塘口基本信息
     * @return
     */
    @ApiOperation(value = "更新塘口",notes = "客户根据id更新塘口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "塘口id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pound", value = "塘口对象", required = true, dataType = "Pound")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Pound updatePound(@PathVariable Integer id,@RequestBody Pound pound){
        return poundService.updatePoundById(id,pound);
    }


    /**
     * 获取塘口
     * @return
     */
    @ApiOperation(value = "获取塘口",notes = "客户获取当前登录用户的所有塘口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public List<Pound> getAllPounds(){
        return poundService.getAllPoundsByCustomer();
    }
}
