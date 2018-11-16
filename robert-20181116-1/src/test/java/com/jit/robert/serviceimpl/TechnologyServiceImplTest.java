package com.jit.robert.serviceimpl;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Expert;
import com.jit.robert.domain.Technology;
import com.jit.robert.dto.TaskDTO;
import com.jit.robert.dto.TechnologyDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TechnologyServiceImplTest {
    @Test
    public void getTechnologyTask() throws Exception {
        List<TaskDTO> taskDTOList = technologyService.getTechnologyTask("hv");
        log.info("taskDTOList = {}",taskDTOList);
        Assert.assertNotEquals(0,taskDTOList.size());
    }

    @Autowired
    private TechnologyServiceImpl technologyService;

    @Test
    public void create() throws Exception {
        Technology technology = new Technology();
        technology.setNumber("T00004");
        technology.setPosition("主管");
        technology.setUsername("hb");
        technology.setTel("12565636715");
        technology.setEmail("hbj@163.com");
        technology.setRealname("寒寒");
        technology.setSex(0);
        technology.setAge(34);
        technology.setAddress("河北");
        TechnologyDTO result = technologyService.create(technology);
        Assert.assertNotNull(result);
    }

    @Test
    public void getTechnologyList() throws Exception {
        PageQO pageQO = new PageQO(1,3);
        PageVO<TechnologyDTO> technologies = technologyService.getAllTechnologies(pageQO);
        log.info("list ={},size={},total={}",technologies.getList(),technologies.getList().size(),technologies.getTotal());
        Assert.assertNotEquals(0,technologies.getList().size());
    }

    @Test
    public void updateTechnology() throws Exception {
        Technology technology = new Technology();
        technology.setPosition("主管");
        technology.setTel("12345678911");
        technology.setEmail("zhu@163.com");
        technology.setAge(35);
        technology.setAddress("宿州");
        Technology result = technologyService.updateTechnology(1,technology);
        Assert.assertNotNull(result);
    }
}