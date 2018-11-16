package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Technology;
import com.jit.robert.dto.TaskDTO;
import com.jit.robert.dto.TechnologyDTO;

import java.util.List;

public interface TechnologyService {

    TechnologyDTO create(Technology technology);

    PageVO<TechnologyDTO> getAllTechnologies(PageQO pageQO);

    PageVO<TechnologyDTO> getTechnologiesByStrategy(QueryStrategy strategy, PageQO pageQO);

    Technology updateTechnology(Integer id, Technology technology);

    Boolean deleteTechnology(String ids);

    List<TaskDTO> getTechnologyTask(String username);

    Boolean updateRepairStatus(Integer id, Integer status);

    TechnologyDTO getTechnologyInfo(Integer id);
}
