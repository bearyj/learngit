package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Repair;
import com.jit.robert.dto.TaskDTO;

public interface RepairService {

    TaskDTO assignTechnology(Integer id, Integer technology_id);

    PageVO<TaskDTO> getAllRepairTasks(PageQO pageQO);

    PageVO<TaskDTO> getRepairTasksByStatus(Integer status, PageQO pageQO);
}
