package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.DrugStore;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.DrugStoreService;
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

@Api(value = "/drugstore",description = "百科---药品库")
@ResponseResult
@RestController
@RequestMapping("/drugstore")
public class DrugStoreController {

    @Autowired
    private DrugStoreService drugStoreService;

    /**
     * 增加药品
     * @return
     */
    @ApiOperation(value = "增加药品",notes = "增加药品详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public DrugStore addDrugStore(MultipartFile image, HttpServletRequest request) throws IOException {
        return drugStoreService.insertDrugStore(image,request);
    }

    /**
     * 删除药品
     * @param id
     * @return
     */
    @ApiOperation(value = "删除药品",notes = "删除药品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "药品id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Boolean deleteDrugStore(@PathVariable Integer id){
        return drugStoreService.deleteDrugStore(id);
    }

    /**
     * 更新药品
     * @param id
     * @return
     */
    @ApiOperation(value = "更新药品",notes = "更新药品详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
             @ApiImplicitParam(name = "id", value = "药品id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public DrugStore updateDrugStore(@PathVariable Integer id,MultipartFile image, HttpServletRequest request) throws IOException {
        return drugStoreService.updateDrugStore(image,request, id);
    }

    /**
     * 获取一条药品详细信息
     * @param id
     * @return
     */
    @ApiOperation(value = "获取一条药品详细信息",notes = "获取一条药品详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "药品id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public DrugStore getOneDrugStore(@PathVariable Integer id){
        return drugStoreService.getDrugStoreInfo(id);
    }

    /**
     * 获取所有药品list
     * @return
     */
    @ApiOperation(value = "获取所有药品list",notes = "获取所有药品list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<DrugStore> getAllDrugStore(PageQO pageQO){
        return drugStoreService.getAllDrugStores(pageQO);
    }

    /**
     * 根据类别获取药品list
     * @param subKind
     * @return
     */
    @ApiOperation(value = "根据类别获取药品list",notes = "根据类别获取药品list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "subKind", value = "药品类别（鱼类、虾类、蟹类或所有）", required = true, dataType = "string")
    })
    @RequestMapping(value = "/subKind",method = RequestMethod.GET)
    public PageVO<DrugStore> getDrugStoreBySubkind(@RequestParam("subKind")String subKind,PageQO pageQO){
        return drugStoreService.getDrugStoreBySubkind(subKind,pageQO);
    }
}
