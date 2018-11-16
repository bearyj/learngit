package com.jit.robert.dto;

import com.jit.robert.domain.Repair;
import com.jit.robert.enums.RepairStatusEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RepairDTO {
    private String number; //机器人编号
    private Integer robert_id;
//    private List<RepairTaskDTO> repairs;
private Integer technology_id;
    private String technology_realname;
    private String technology_username;
    private String technology_tel;
    private String type;
    private String description;
    private Integer status;
    private Date time;
}
