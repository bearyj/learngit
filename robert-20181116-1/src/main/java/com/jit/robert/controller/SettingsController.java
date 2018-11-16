package com.jit.robert.controller;

import com.jit.robert.domain.Settings;
import com.jit.robert.dto.SettingsDTO;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.SettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "settings", description = "配置管理")
@RestController
@ResponseResult
@RequestMapping("/settings")
public class SettingsController {
    @Autowired
    private SettingsService settingsService;

    /**
     * 增加配置
     * @param settings
     * @return
     */
    @ApiOperation(value = "增加配置",notes = "增加水产机器人配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "settings", value = "配置对象", required = true, dataType = "Settings")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public SettingsDTO insertSettings(@RequestBody  Settings settings){
        return settingsService.insertSettings(settings);
    }

    /**
     * 删除配置
     * @param id
     * @return
     */
    @ApiOperation(value = "删除配置",notes = "根据id删除水产机器人配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "配置id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Boolean deleteSettings(@PathVariable Integer id){
        return settingsService.deleteSettings(id);
    }

    /**
     * 更新配置
     * @param id
     * @param settings
     * @return
     */
    @ApiOperation(value = "更新配置",notes = "根据id更新水产机器人配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "配置id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "settings", value = "配置对象", required = true, dataType = "Settings")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public SettingsDTO updateSettings(@PathVariable Integer id, @RequestBody Settings settings){
        return settingsService.updateSettings(settings,id);
    }

    /**
     * 获取配置
     * @param pound_id
     * @return
     */
    @ApiOperation(value = "获取配置",notes = "获取某一塘口下水产机器人的配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pound_id", value = "塘口id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{pound_id}",method = RequestMethod.GET)
    public SettingsDTO getSettingsByPound(@PathVariable Integer pound_id){
        return settingsService.getAllSettingsByPound(pound_id);
    }

    @ApiOperation(value = "获取配置",notes = "获取某一用户下水产机器人的配置")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<SettingsDTO> getSettingsByUser(){
        return settingsService.getAllSettingsByUser();
    }
}
