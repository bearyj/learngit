package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Technology;
import com.jit.robert.dto.TaskDTO;
import com.jit.robert.dto.TechnologyDTO;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.TechnologyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "technology", description = "技术人员管理")
@ResponseResult
@RestController
@RequestMapping(value = "/technology")
public class TechnologyController {


    @Autowired
    private TechnologyService technologyService;

    /**
     * 管理员录入技术人员信息
     * @return
     */
    @ApiOperation(value = "技术人员注册",notes = "管理员录入技术人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "technology", value = "技术人员对象", required = true, dataType = "Technology")
    })
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public TechnologyDTO createExpert(@RequestBody Technology technology){
        return technologyService.create(technology);
    }

    /**
     * 查看所有技术人员信息，分页展示
     * @param pageQO
     * @return
     */
    @ApiOperation(value = "查看技术人员信息",notes = "查看所有技术人员信息，分页展示")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<TechnologyDTO> getAllTechnologies(PageQO pageQO){
        return technologyService.getAllTechnologies(pageQO);
    }

    /**
     * 根据省市地区或者注册时间筛选技术人员
     * @param queryStrategy
     * @param pageQO
     * @return
     */
    @ApiOperation(value = "筛选技术人员",notes = "根据省市地区或者注册时间筛选技术人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "queryStrategy", value = "筛选条件对象", required = true, dataType = "QueryStrategy")
    })
    @RequestMapping(value = "/strategy",method = RequestMethod.POST)
    public PageVO<TechnologyDTO> getTechnologiesByStrategy(@RequestBody QueryStrategy queryStrategy, PageQO pageQO){
        return technologyService.getTechnologiesByStrategy(queryStrategy, pageQO);
    }

    /**
     * 更新技术人员信息，可以是管理员也可以是技术人员自己
     * @return
     */
    @ApiOperation(value = "更新技术人员",notes = "更新技术人员信息，可以是管理员也可以是技术人员自己")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "技术人员id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "technology", value = "需更新的技术人员对象（可更新字段：realname, sex, position, tel, email, age, province, city, county, address）", required = true, dataType = "Technology")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Technology updateTechnology (@PathVariable Integer id, @RequestBody Technology technology){
        return technologyService.updateTechnology(id, technology);
    }

    /**
     * 删除技术人员，仅管理员可删，可单个可批量
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除技术人员",notes = "删除技术人员，仅管理员可删，可单个可批量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "需要删除的技术人员的id（多个用“-”连接，如：1-3-4）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public Boolean deleteTechnology(@PathVariable("ids") String ids){
        return technologyService.deleteTechnology(ids);
    }

    /**
     * 获取某一技术人员的任务列表
     * @param username
     * @return
     */
    @ApiOperation(value = "获取任务列表",notes = "获取某一技术人员的任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "需要获取技术人员的名字", required = true, dataType = "String")
    })
    @RequestMapping(value = "/task",method = RequestMethod.GET)
    public List<TaskDTO> getTechnologyTask(@RequestParam String username){
        return technologyService.getTechnologyTask(username);
    }

    /**
     * 技术人员更新维修状态
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "更新维修状态",notes = "技术人员更新维修状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "维修任务id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "status", value = "需要更新的状态",required = true,dataType = "int")
    })
    @RequestMapping(value = "/repair/{id}", method = RequestMethod.PUT)
    public Boolean updateRepairStatus(@PathVariable Integer id,@RequestParam Integer status){
        return technologyService.updateRepairStatus(id, status);
    }

    @ApiOperation(value = "获取技术人员信息",notes = "获取技术人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public TechnologyDTO getCurrentUserInfo(@PathVariable Integer id){
        return technologyService.getTechnologyInfo(id);
    }

}
