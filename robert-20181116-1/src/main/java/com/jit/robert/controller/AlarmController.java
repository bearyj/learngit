package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Alarm;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.AlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "alarm", description = "报警管理")
@ResponseResult
@RestController
@RequestMapping("/alarm")
public class AlarmController {
    @Autowired
    private AlarmService alarmService;

    /**
     * 增加报警配置
     * @param alarm 报警配置对象
     * @return
     */
    @ApiOperation(value = "增加报警配置",notes = "增加水产机器人报警配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "alarm", value = "报警配置对象", required = true, dataType = "Alarm")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Alarm insertAlarm(@RequestBody Alarm alarm){
        return alarmService.insertAlarm(alarm);
    }

    /**
     * 更新报警配置
     * @param id 报警配置id
     * @param alarm 报警配置对象
     * @return
     */
    @ApiOperation(value = "更新报警配置",notes = "更新水产机器人报警配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "alarm", value = "报警配置对象", required = true, dataType = "Alarm"),
            @ApiImplicitParam(name = "id", value = "报警配置id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Boolean updateAlarm(@PathVariable Integer id,@RequestBody Alarm alarm){
        return alarmService.updateAlarm(alarm,id);
    }

    /**
     * 删除报警配置
     * @param id 报警配置id
     * @return
     */
    @ApiOperation(value = "删除报警配置",notes = "删除水产机器人报警配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "报警配置id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Boolean deleteAlarm(@PathVariable Integer id){
        return alarmService.deleteAlarm(id);
    }

    /**
     * 获取报警配置
     * @param robert_id 机器人id
     * @return
     */
    @ApiOperation(value = "获取报警配置",notes = "根据水产机器人id获取报警配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "robert_id", value = "机器人id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{robert_id}",method = RequestMethod.GET)
    public Alarm getAlarmByRobert(@PathVariable Integer robert_id){
        return alarmService.getAlarmByRobert(robert_id);
    }

    /**
     * 获取报警配置
     * @return
     */
    @ApiOperation(value = "获取所有报警配置",notes = "获取所有水产机器人的报警配置")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public PageVO<Alarm> getAlarmOfRobert(PageQO pageQO){
        return alarmService.getAlarmOfRobert(pageQO);
    }
}
