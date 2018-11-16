package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Feed;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.FeedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@Api(value = "feed",description = "日常投喂")
@RestController
@ResponseResult
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private FeedService feedService;


//    /**
//     * 插入投喂信息,已有当天的投喂信息则更新
//     * @param feed
//     * @return
//     */
//    @RequestMapping(value = "/insert",method = RequestMethod.POST)
//    public Feed insertFeed(@RequestBody Feed feed){
//        return feedService.insert(feed);
//    }

    /**
     * 插入投喂信息
     * @return
     * @throws ParseException
     */
    @ApiOperation(value = "插入投喂信息",notes = "插入投喂信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Feed insertFeed(HttpServletRequest request) throws ParseException {
        return feedService.insert(request);
    }

    /**
     * 获取某一塘口的所有投喂信息
     * @param pound_id
     * @return
     */
    @ApiOperation(value = "获取某一塘口的所有投喂信息",notes = "获取某一塘口的所有投喂信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pound_id", value = "塘口id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{pound_id}",method = RequestMethod.GET)
    public PageVO<Feed> getWaterByPound(@PathVariable Integer pound_id, PageQO pageQO){
        return feedService.getFeedByPound(pound_id,pageQO);
    }
}
