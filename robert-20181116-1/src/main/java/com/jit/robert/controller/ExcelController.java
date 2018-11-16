package com.jit.robert.controller;

import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(value = "excel", description = "excel表格下载")
@RestController
@ResponseResult
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    /**
     * 下载机器人列表
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "下载机器人列表",notes = "下载机器人列表")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/robert",method = RequestMethod.GET)
    public void exportRobertExcel(HttpServletResponse response) throws Exception{
        excelService.exportRobertExcel(response);
    }

    /**
     * 下载管理员列表
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "下载管理员列表",notes = "下载管理员列表")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public void exportAdminExcel(HttpServletResponse response) throws Exception{
        excelService.exportAdminExcel(response);
    }

    /**
     * 下载技术人员列表
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "下载技术人员列表",notes = "下载技术人员列表")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/technology",method = RequestMethod.GET)
    public void exportTechnologyExcel(HttpServletResponse response) throws Exception{
        excelService.exportTechnologyExcel(response);
    }

    /**
     * 下载技术员任务列表
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "下载技术员任务列表",notes = "下载技术员任务列表")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/repair",method = RequestMethod.GET)
    public void exportTechnologyTaskExcel(HttpServletResponse response) throws Exception{
        excelService.exportTechnologyTaskExcel(response);
    }

    /**
     * 下载机器人参数列表
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "下载机器人参数列表",notes = "下载机器人参数列表")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/alarm",method = RequestMethod.GET)
    public void exportAlarmExcel(HttpServletResponse response) throws Exception{
        excelService.exportRobertParamExcel(response);
    }

    /**
     * 下载专家信息列表
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "下载专家信息列表",notes = "下载专家信息列表")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/expert",method = RequestMethod.GET)
    public void exportExpertExcel(HttpServletResponse response) throws Exception{
        excelService.exportExpertExcel(response);
    }

    /**
     * 下载客户信息列表
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "下载客户信息列表",notes = "下载客户信息列表")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/customer",method = RequestMethod.GET)
    public void exportCustomerExcel(HttpServletResponse response) throws Exception{
        excelService.exportCustomerExcel(response);
    }
}
