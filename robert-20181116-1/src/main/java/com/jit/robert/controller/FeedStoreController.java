package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.FeedStore;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.FeedStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(value = "feedStore",description = "百科---投喂库")
@RestController
@ResponseResult
@RequestMapping(value = "/feedStore")
public class FeedStoreController {

    @Autowired
    private FeedStoreService feedStoreService;

    /**
     * 增加投喂相关的百科
     * @param image
     * @param request
     * @return
     */
    @ApiOperation(value = "增加投喂",notes = "增加投喂百科详细信息")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public FeedStore createFeedStore(MultipartFile image, HttpServletRequest request) throws IOException {
        return feedStoreService.create(image, request);
    }

    /**
     * 删除投喂百科
     * @param id
     * @return
     */
    @ApiOperation(value = "删除某一条投喂百科",notes = "删除某一条投喂百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "投喂百科id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Boolean deleteFeedStore(@PathVariable Integer id){
        return feedStoreService.deleteFeedStore(id);
    }

    /**
     * 修改投喂百科
     * @param id
     * @param image
     * @param request
     * @return
     */
    @ApiOperation(value = "修改某一条投喂百科",notes = "修改某一条投喂百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "投喂百科id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public FeedStore updateFeedStore(@PathVariable Integer id, MultipartFile image, HttpServletRequest request) throws IOException {
        return feedStoreService.updateFeedStore(id,image, request);
    }

    /**
     * 获取一条投喂百科详情
     * @param id
     * @return
     */
    @ApiOperation(value = "获取某一个投喂百科的详细信息",notes = "获取某一个投喂百科的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "投喂百科id", required = true, dataType = "int")
    })
   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FeedStore getOneFeedStore(@PathVariable Integer id){
        return feedStoreService.getFeedStoreInfo(id);
    }

    /**
     * 获取所有百科
     * @return
     */
    @ApiOperation(value = "获取所有投喂百科",notes = "获取所有投喂百科")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<FeedStore> getAllFeedStores(PageQO pageQO){
        return feedStoreService.getAllFeedStores(pageQO);
    }

    /**
     * 根据类别获取百科
     * @param subKind
     * @return
     */
    @ApiOperation(value = "获取某一类别的投喂百科",notes = "获取某一类别的投喂百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "subKind", value = "类别（鱼类、虾类、蟹类或所有）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/subKind",method = RequestMethod.GET)
    public PageVO<FeedStore> getFeedStoresBySubKind(@RequestParam("subKind") String subKind, PageQO pageQO){
        return feedStoreService.getFeedStoresBySubKind(subKind,pageQO);
    }

}
