package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Water;
import com.jit.robert.dto.DiaryDTO;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.WaterService;
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


@Api(value = "water",description = "水质管理")
@RestController
@ResponseResult
@RequestMapping("/water")
public class WaterController {

    @Autowired
    private WaterService waterService;

    /**
     * 插入水质信息
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "插入水质信息",notes = "插入水质信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")

    })
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Water insertWater( MultipartFile[] image,HttpServletRequest request) throws IOException {
        return waterService.insert(image,request);
    }

    /**
     * 删除某一条水质信息
     * @param id
     * @return
     */
    @ApiOperation(value = "插入水质信息",notes = "插入水质信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "水质信息id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Boolean deleteWater(@PathVariable("id")Integer id){
        return waterService.delete(id);
    }

    /**
     * 更新某一条水质信息
     * @param water
     * @return
     */
    @ApiOperation(value = "更新某一条水质信息",notes = "更新某一条水质信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "water", value = "水质信息", required = true, dataType = "Water")
    })
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Water updateWater(@RequestBody Water water){
        return waterService.update(water);
    }

    /**
     * 获取某一塘口的所有水质信息
     * @param pound_id
     * @return
     */
    @ApiOperation(value = "获取某一塘口的所有水质信息",notes = "获取某一塘口的所有水质信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pound_id", value = "水质信息id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{pound_id}",method = RequestMethod.GET)
    public PageVO<Water> getWaterByPound(@PathVariable Integer pound_id, PageQO pageQO){
        return waterService.getWaterByPound(pound_id,pageQO);
    }

    @ApiOperation(value = "获取某一塘口的所有日志信息",notes = "获取某一塘口的所有日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pound_id", value = "水质信息id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "start_date", value = "起始日期", required = true, dataType = "string"),
            @ApiImplicitParam(name = "end_date", value = "结束日期", required = true, dataType = "string")
    })
    @RequestMapping(value = "/diary",method = RequestMethod.GET)
    public List<DiaryDTO> getDiaryByPound(@RequestParam("pound_id")Integer pound_id,@RequestParam("start_date")String start_date,@RequestParam("end_date")String end_date){
        return waterService.getDiaryByPound(pound_id, start_date, end_date);
    }
}
