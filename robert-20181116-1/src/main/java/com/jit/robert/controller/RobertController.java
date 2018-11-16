package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Robert;
import com.jit.robert.dto.RobertDTO;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.RobertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "robert",description = "机器人管理")
@RestController
@ResponseResult
@RequestMapping("/robert")
public class RobertController {
    @Autowired
    private RobertService robertService;

    /**
     * 增加机器人
     * @param robert
     * @return
     */
    @ApiOperation(value = "增加机器人",notes = "管理员和客户可增加机器人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "robert", value = "机器人对象", required = true, dataType = "Robert")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public RobertDTO insertRobert(@RequestBody Robert robert){
        return robertService.insertRobert(robert);
    }

    /**
     * 分配机器人
     * @param robert_id
     * @param user_id
     * @return
     */
    @ApiOperation(value = "分配机器人",notes = "管理员为客户分配机器人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "robert_id", value = "机器人id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "user_id",value = "客户id",required = true,dataType = "int")
    })
    @RequestMapping(value = "/assign",method = RequestMethod.POST)
    Robert assignRobertByAdmin(@RequestParam("robert_id") Integer robert_id, @RequestParam("user_id") Integer user_id){
        return robertService.assignRobertByAdmin(robert_id, user_id);
    }

    /**
     * 删除机器人
     * @param id
     * @return
     */
    @ApiOperation(value = "删除机器人",notes = "管理员和客户可删除机器人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "机器人id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    Boolean deleteRobert(@PathVariable Integer id){
        return robertService.deleteRobert(id);
    }

    /**
     * 更新机器人
     * @param robert
     * @param id
     * @return
     */
    @ApiOperation(value = "更新机器人",notes = "管理员和客户可更新机器人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "机器人id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    RobertDTO updateRobert(@RequestBody Robert robert,@PathVariable  Integer id){
        return robertService.updateRobert(robert,id);
    }

    /**
     * 根据客户获取机器人(安卓端传入的是当前登录的普通用户，传入的是user_id,web端传入的是customer_id)
     * @param customer_id
     * @return
     */
    @ApiOperation(value = "根据客户获取机器人",notes = "管理员和客户可根据客户id获取机器人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "customer_id", value = "客户id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/customer",method = RequestMethod.GET)
    PageVO<RobertDTO> getAllRobertsByCustomer(@RequestParam("customer_id") Integer customer_id, PageQO pageQO){
        return robertService.getAllRobertsByCustomer(customer_id,pageQO);
    }

    /**
     * 根据类型获取机器人
     * @param type
     * @return
     */
    @ApiOperation(value = "根据类型获取机器人",notes = "管理员根据类型获取机器人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "机器人类型", required = true, dataType = "string")
    })
    @RequestMapping(value = "/type",method = RequestMethod.GET)
    PageVO<RobertDTO> getAllRobertsByType(@RequestParam("type") String type, PageQO pageQO){
        return robertService.getAllRobertsByType(type,pageQO);
    }

    /**
     * 获取所有机器人
     * @return
     */
    @ApiOperation(value = "获取所有机器人",notes = "管理员获取所有机器人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    PageVO<RobertDTO> getAllRoberts(PageQO pageQO){
        return robertService.getAllRoberts(pageQO);
    }


    /**
     * 获取所有机器人类型
     * @return
     */
    @ApiOperation(value = "获取所有机器人类型",notes = "管理员获取所有机器人类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/all/type",method = RequestMethod.GET)
    List<String> getAllRobertsType(PageQO pageQO){
        return robertService.getAllRobertType();
    }


    /**
     * 获取所有机器人编号
     * @return
     */
    @ApiOperation(value = "获取所有机器人编号",notes = "管理员获取所有机器人编号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/all/number",method = RequestMethod.GET)
    List<Robert> getAllRobertsNumber(){
        return robertService.getAllRobertsWithoutPage();
    }


}
