package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.FeedStore;
import com.jit.robert.domain.SeedStore;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.SeedStoreService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(value = "种苗",description = "百科---种苗库")
@ResponseResult
@RestController
@RequestMapping(value = "/seedStore")
public class SeedStoreController {

    @Autowired
    private SeedStoreService seedStoreService;

    /**
     * 增加种苗相关的百科
     * @param image
     * @param request
     * @return
     */
    @ApiOperation(value = "增加一条种苗百科",notes = "增加一条种苗百科")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public SeedStore createSeedStore(MultipartFile image, HttpServletRequest request) throws IOException {
        return seedStoreService.create(image, request);
    }

    /**
     * 删除种苗百科
     * @param id
     * @return
     */
    @ApiOperation(value = "删除某一条种苗百科",notes = "删除某一条种苗百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "种苗百科id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Boolean deleteSeedStore(@PathVariable Integer id){
        return seedStoreService.deleteSeedStore(id);
    }

    /**
     * 修改种苗百科
     * @param id
     * @param image
     * @param request
     * @return
     */
    @ApiOperation(value = "修改某一条种苗百科",notes = "修改某一条种苗百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "投喂百科id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public SeedStore updateSeedStore(@PathVariable Integer id, MultipartFile image, HttpServletRequest request) throws IOException {
        return seedStoreService.updateSeedStore(id,image,request);
    }

    /**
     * 获取一条种苗百科详情
     * @param id
     * @return
     */
    @ApiOperation(value = "获取某一条种苗百科的详细信息",notes = "获取某一条种苗百科的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "种苗百科id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SeedStore getOneSeedStore(@PathVariable Integer id){
        return seedStoreService.getSeedStoreInfo(id);
    }

    /**
     * 获取所有百科
     * @return
     */
    @ApiOperation(value = "获取所有种苗百科",notes = "获取所有种苗百科")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<SeedStore> getAllSeedStores(PageQO pageQO){
        return seedStoreService.getAllSeedStores(pageQO);
    }

    /**
     * 根据类别获取百科
     * @param subKind
     * @return
     */
    @ApiOperation(value = "获取某一类别的种苗百科",notes = "获取某一类别的种苗百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "subKind", value = "类别（鱼类、虾类、蟹类或所有）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/subKind",method = RequestMethod.GET)
    public PageVO<SeedStore> getSeedStoresBySubKind(@RequestParam("subKind") String subKind, PageQO pageQO){
        return seedStoreService.getSeedStoresBySubKind(subKind,pageQO);
    }

}
