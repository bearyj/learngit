package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Repair;
import com.jit.robert.domain.Technology;
import com.jit.robert.dto.TaskDTO;
import com.jit.robert.dto.TechnologyDTO;
import com.jit.robert.mapper.RepairMapper;
import com.jit.robert.mapper.TechnologyMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.RepairService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private TechnologyMapper technologyMapper;

    @Override
    public TaskDTO assignTechnology(Integer id, Integer technology_id) {
        Repair repair = repairMapper.getRepairById(id);
        if (repair.getTechnology_id()!=null) {
            if (repair.getStatus() == 2 || repair.getStatus() == 3){
                throw new BusinessException(ResultCode.DATA_IS_DONE);
            }
            repairMapper.updateRepairTechnology(id,technology_id);
        }else {
            repairMapper.updateRepairTechnology(id,technology_id);
        }
        Technology technology = technologyMapper.getTechnologyById(technology_id);
        TaskDTO taskDTO = repairMapper.getTaskByRepairId(id);
        taskDTO.setTechnology_name(technology.getRealname());
        return taskDTO;
    }

    @Override
    public PageVO<TaskDTO> getAllRepairTasks(PageQO pageQO) {
        Page<TaskDTO> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<TaskDTO> repairTasks = repairMapper.getAllRepairTasks();
        for (TaskDTO taskDTO: repairTasks){
            if (taskDTO.getTechnology_id() == null){
                taskDTO.setTechnology_name(null);
            }else {
                Technology technology = technologyMapper.getTechnologyById(taskDTO.getTechnology_id());
                taskDTO.setTechnology_name(technology.getRealname());
                taskDTO.setTechnology_username(technology.getUsername());
            }
        }
        return PageVO.build(page);
    }

    @Override
    public PageVO<TaskDTO> getRepairTasksByStatus(Integer status,PageQO pageQO) {
        Page<TaskDTO> page = PageHelper.startPage(pageQO.getPageNum(), pageQO.getPageSize());
        List<TaskDTO> repairTasks;
        log.info("status = {}",status);
        if (status == 0){
            repairTasks = repairMapper.getRepairTasksByStatus(status);
        }else {
            repairTasks = repairMapper.getRepairTasksByStatus(status);
            for (TaskDTO taskDTO: repairTasks){
                Technology technology = technologyMapper.getTechnologyById(taskDTO.getTechnology_id());
                taskDTO.setTechnology_name(technology.getRealname());
                taskDTO.setTechnology_username(technology.getUsername());
            }
        }
        return PageVO.build(page);
    }
}
