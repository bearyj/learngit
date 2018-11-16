package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Admin;
import com.jit.robert.dto.AdminDTO;
import com.jit.robert.dto.TaskDTO;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.AdminService;
import com.jit.robert.serviceinterface.RepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "admin", description = "管理员相关")
@ResponseResult
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private RepairService repairService;

    @Autowired
    private AdminService adminService;

    /**
     * 管理员分配售后任务,或者更改在维修中的技术人员
     * @param id
     * @param technology_id
     * @return
     */
    @ApiOperation(value = "售后任务的分配",notes = "管理员分配售后任务,或者更改在维修中的技术人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "维修任务的id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "technology_id", value = "技术人员的id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public TaskDTO assignTechnology(@PathVariable Integer id, @RequestParam Integer technology_id){
        return repairService.assignTechnology(id, technology_id);
    }

    /**
     * 管理员获取所有维修任务
     * @param pageQO
     * @return
     */
    @ApiOperation(value = "获取所有维修任务",notes = "管理员获取所有维修任务")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public PageVO<TaskDTO> getAllRepairTasks(PageQO pageQO){
        return repairService.getAllRepairTasks(pageQO);
    }

    /**
     * 管理员根据状态获取不同的维修任务列表
     * @param status
     * status = 0, 待维修，即获取尚未分配技术人员的售后任务
     * status = 1，处于维修中的任务
     * status = 2，已完成的维修任务
     * status = 3，损耗太大，无法维修的任务
     * @return
     */
    @ApiOperation(value = "根据维修状态获取维修任务",notes = "管理员根据状态获取不同的维修任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "维修任务状态（status = 0, 待维修，即获取尚未分配技术人员的售后任务；status = 1，处于维修中的任务；" +
                    "status = 2，已完成的维修任务；status = 3，损耗太大，无法维修的任务）", required = true, dataType = "int")
    })
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public PageVO<TaskDTO> getRepairTasksByStatus(@RequestParam Integer status, PageQO pageQO){
        return repairService.getRepairTasksByStatus(status, pageQO);
    }

    /**
     * 超级管理员注册一般管理员
     * @param admin
     * @return
     */
    @ApiOperation(value = "注册一般管理员",notes = "超级管理员注册一般管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "admin", value = "管理员对象", required = true, dataType = "Admin")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Admin create(@RequestBody Admin admin){
        return adminService.create(admin);
    }

    /**
     * 超级管理员获取管理员列表
     * @param pageQO
     * @return
     */
    @ApiOperation(value = "获取管理员列表",notes = "超级管理员获取管理员列表")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageVO<AdminDTO> getAllAdmins(PageQO pageQO){
        return adminService.getAllAdmins(pageQO);
    }

    /**
     * 启用或禁用一般管理员的权限
     * @param status
     * @param username
     * @return
     */
    @ApiOperation(value = "启用或禁用一般管理员的权限",notes = "超级管理员启用或禁用一般管理员的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "权限状态标志（0：禁用；1：启用）", required = true, dataType = "int"),
            @ApiImplicitParam(name = "username", value = "一般管理员的名字", required = true, dataType = "String")
    })
    @RequestMapping(value = "/power",method = RequestMethod.PUT)
    public Admin updateUserPower(@RequestParam Integer status, @RequestParam String username){
        return adminService.updateUserPower(status, username);
    }

    @ApiOperation(value = "更新管理员信息",notes = "更新管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "department", value = "部门", required = true, dataType = "string"),
            @ApiImplicitParam(name = "username", value = "一般管理员的名字", required = true, dataType = "string"),
            @ApiImplicitParam(name = "remark", value = "备注", required = true, dataType = "string")
    })
    @RequestMapping(value = "/info",method = RequestMethod.PUT)
    public Admin updateUserInfo(@RequestParam("department")String department,@RequestParam("remark")String remark,@RequestParam("username")String username){
        return adminService.updateAdminInfo(username,department, remark);
    }

    @ApiOperation(value = "初始化管理员密码",notes = "初始化管理员密码，为123456,初始化密码之前需要禁用用户权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "一般管理员的名字", required = true, dataType = "string")
    })
    @RequestMapping(value = "/password",method = RequestMethod.PUT)
    public Boolean updatePassword(@RequestParam("username")String username){
        return adminService.updatePassword(username);
    }

    @ApiOperation(value = "获取一般管理员信息",notes = "获取一般管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public AdminDTO getCurrentUserInfo(@PathVariable Integer id){
        return adminService.getAdminInfo(id);
    }

 }
