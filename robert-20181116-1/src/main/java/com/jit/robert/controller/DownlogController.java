package com.jit.robert.controller;

import com.jit.robert.domain.Downlog;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.DownlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Api(value = "downlog",description = "下载日志记录")
@RestController
@ResponseResult
@RequestMapping("/downlog")
public class DownlogController {

    @Autowired
    private DownlogService downlogService;

    /**
     * 增加下载记录，已经下载过的覆盖
     * @param downlogname
     * @return
     * @throws ParseException
     */
    @ApiOperation(value = "增加下载记录",notes = "增加下载记录，已经下载过的覆盖")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "downlogname", value = "下载记录名称", required = true, dataType = "string")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Downlog addDownlog(@RequestParam("downlogname")String downlogname) throws ParseException {
        return downlogService.addDownlog(downlogname);
    }

    /**
     * 批量删除下载记录，用逗号隔开
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除下载记录",notes = "批量删除下载记录，用逗号隔开")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "下载记录id", required = true, dataType = "string")
    })
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    public Boolean deleteDownlog(@PathVariable  String ids){
        return downlogService.deleteDownlogBatch(ids);
    }

    /**
     * 获取所有下载记录
     * @return
     */
    @ApiOperation(value = "获取所有下载记录",notes = "获取所有下载记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Downlog> getAllDownlogs(){
        return downlogService.getAllDownlog();
    }
}
