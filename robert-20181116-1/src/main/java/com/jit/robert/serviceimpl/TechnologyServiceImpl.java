package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Repair;
import com.jit.robert.domain.Technology;
import com.jit.robert.domain.User;
import com.jit.robert.dto.TaskDTO;
import com.jit.robert.dto.TechnologyDTO;
import com.jit.robert.enums.RepairStatusEnum;
import com.jit.robert.enums.UserTypeEnum;
import com.jit.robert.mapper.RepairMapper;
import com.jit.robert.mapper.TechnologyMapper;
import com.jit.robert.mapper.UserMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.TechnologyService;
import com.jit.robert.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TechnologyServiceImpl implements TechnologyService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TechnologyMapper technologyMapper;

    @Autowired
    private RepairMapper repairMapper;
    @Value("${image.url}")
    private String url;
    @Override
    public TechnologyDTO create(Technology technology) {
        if (userMapper.findByUsername(technology.getUsername()) != null){
            throw  new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        //1、注册专业技术人员
        User user = UserUtil.RegisterUser(technology.getUsername());
        userMapper.insert(user);
        //2、设置角色
        User currentUser = userMapper.findByUsername(technology.getUsername());
        userMapper.insertUserRole(currentUser.getId(),3);
        //3、插入技术人员列表
        technology.setEnter_time(new Date());
        technologyMapper.insert(technology);
        return technologyMapper.getByUsername(technology.getUsername());
    }

    @Override
    public PageVO<TechnologyDTO> getAllTechnologies(PageQO pageQO) {
        Page<TechnologyDTO> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<TechnologyDTO> expertList = technologyMapper.getAllTechnologies();
        return PageVO.build(page);
    }

    @Override
    public PageVO<TechnologyDTO> getTechnologiesByStrategy(QueryStrategy strategy, PageQO pageQO) {
        Page<TechnologyDTO> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<TechnologyDTO> technologies = technologyMapper.getTechnologiesByStrategy(UserTypeEnum.TECHNOLOGY.getCode(),strategy);
        return PageVO.build(page);
    }

    @Override
    public Technology updateTechnology(Integer id, Technology technology) {
        Technology currentTechnology = technologyMapper.getTechnologyById(id);
        if (currentTechnology == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        currentTechnology.setRealname(technology.getRealname());
        currentTechnology.setSex(technology.getSex());
        currentTechnology.setPosition(technology.getPosition());
        currentTechnology.setTel(technology.getTel());
        currentTechnology.setEmail(technology.getEmail());
        currentTechnology.setAge(technology.getAge());
        currentTechnology.setProvince(technology.getProvince());
        currentTechnology.setCity(technology.getCity());
        currentTechnology.setCounty(technology.getCounty());
        currentTechnology.setAddress(technology.getAddress());
        technologyMapper.updateTechnology(currentTechnology);
        return technologyMapper.getTechnologyById(id);
    }

    @Override
    public Boolean deleteTechnology(String ids) {
        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            List<Integer> userIds = technologyMapper.getUserIds(UserTypeEnum.TECHNOLOGY.getCode(),delIds.substring(1,delIds.length()-1));
            String del_userIds = userIds.toString();
            userMapper.deleteUserBatch(del_userIds.substring(1,del_userIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            Integer user_id = technologyMapper.getUserId(id);
            userMapper.deleteUser(user_id);
        }
        return true;
    }

    @Override
    public List<TaskDTO> getTechnologyTask(String username) {
        Integer technology_id = technologyMapper.getTechnologyId(username);
        List<TaskDTO> taskDTOs = repairMapper.getTaskByTechnologyId(technology_id);
        TechnologyDTO technology = technologyMapper.getByUsername(username);
        for (TaskDTO taskDTO: taskDTOs){
            taskDTO.setTechnology_name(technology.getRealname());
            taskDTO.setTechnology_username(technology.getUsername());
        }
        return taskDTOs;
    }

    @Override
    public Boolean updateRepairStatus(Integer id, Integer status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer technology_id = technologyMapper.getTechnologyId(username);
        Repair repair = repairMapper.getRepairById(id);
        if (!repair.getTechnology_id().equals(technology_id)){
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }
        if (status.equals(2)){
            repair.setStatus(RepairStatusEnum.REPAIR_DONE.getCode());
        }else if (status.equals(3)){
            repair.setStatus(RepairStatusEnum.REPAIR_IS_FAIL.getCode());
        }else {
            repair.setStatus(RepairStatusEnum.REPAIRING.getCode());
        }
        int flag = repairMapper.updateRepairStatus(id,repair);
        if (flag > 0){
            return true;
        }
        return false;
    }

    @Override
    public TechnologyDTO getTechnologyInfo(Integer id) {
        TechnologyDTO technologyDTO = technologyMapper.getTechnologyDTOById(id);
        changeImage(technologyDTO);
        return technologyDTO;
    }
    private void changeImages(List<TechnologyDTO> list){
        for (int i=0;i<list.size();i++){
            changeImage(list.get(i));
        }
    }

    private void changeImage(TechnologyDTO obj){
        if (obj.getImage()!=null){
            obj.setImage(url+obj.getImage());
        }
    }
}
