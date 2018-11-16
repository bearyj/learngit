package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Alarm;
import com.jit.robert.domain.Expert;
import com.jit.robert.mapper.AlarmMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmServiceImpl implements AlarmService {
    @Autowired
    private AlarmMapper alarmMapper;
    @Override
    public Alarm insertAlarm(Alarm alarm) {
        //一台机器人只有一个报警配置
        Alarm isExist = alarmMapper.getAlarmByRobert(alarm.getRobert_id());
        if (isExist!=null){
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        int flag = alarmMapper.insert(alarm);
        if (flag>0){
            return alarm;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    @Override
    public Boolean deleteAlarm(Integer id) {
        int flag = alarmMapper.delete(id);
        if (flag>0){
            return true;
        }else {
            throw new BusinessException(ResultCode.DATABASE_DELETE_ERROR);
        }
    }


    @Override
    public Boolean updateAlarm(Alarm alarm, Integer id) {
        //一台机器人只有一个报警配置
        Alarm isExist = alarmMapper.getAlarmByRobert(alarm.getRobert_id());
        if (isExist==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }

        alarm.setId(id);
        int flag = alarmMapper.update(alarm);
        if (flag>0){
            return true;
        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }

    @Override
    public Alarm getAlarmByRobert(Integer robert_id) {
        Alarm alarm = alarmMapper.getAlarmByRobert(robert_id);
        return alarm;
    }

    @Override
    public PageVO<Alarm> getAlarmOfRobert(PageQO pageQO) {
        Page<Alarm> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Alarm> allExperts = alarmMapper.getAllAlarm();
        return PageVO.build(page);
    }
}
