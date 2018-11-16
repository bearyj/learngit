package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Alarm;

public interface AlarmService {
    Alarm insertAlarm(Alarm alarm);
    Boolean deleteAlarm(Integer id);
    Boolean updateAlarm(Alarm alarm,Integer id);
    Alarm getAlarmByRobert(Integer robert_id);
    PageVO<Alarm> getAlarmOfRobert(PageQO pageQO);
}
