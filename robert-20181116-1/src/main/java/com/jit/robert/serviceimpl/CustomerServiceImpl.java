package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.*;
import com.jit.robert.dto.CustomerDTO;
import com.jit.robert.dto.RepairDTO;
import com.jit.robert.enums.RepairStatusEnum;
import com.jit.robert.enums.UserTypeEnum;
import com.jit.robert.mapper.CustomerMapper;
import com.jit.robert.mapper.RepairMapper;
import com.jit.robert.mapper.RobertMapper;
import com.jit.robert.mapper.UserMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.CustomerService;
import com.jit.robert.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private RobertMapper robertMapper;


    @Override
    public Customer create(Customer customer) {
        if (userMapper.findByUsername(customer.getUsername()) != null){
            throw  new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        //1、注册普通用户
        User user = UserUtil.RegisterUser(customer.getUsername());
        userMapper.insert(user);
        //2、设置角色
        User currentUser = userMapper.findByUsername(customer.getUsername());
        userMapper.insertUserRole(currentUser.getId(),4);
        //3、插入普通用户列表
        customerMapper.insert(customer);
        return customerMapper.getByUsername(customer.getUsername());
    }

    @Override
    public PageVO<Customer> getAllCustomers(PageQO pageQO) {
        Page<Customer> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Customer> customerList = customerMapper.getAllCustomers();
        return PageVO.build(page);
    }

    @Override
    public PageVO<Customer> getCustomersByStrategy(QueryStrategy strategy, PageQO pageQO) {
        Page<Customer> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Customer> customerList = customerMapper.getCustomersByStrategy(UserTypeEnum.CUSTOMER.getCode(),strategy);
        return PageVO.build(page);
    }

    @Override
    public Customer updateCustomer(Integer id, Customer customer) {
        Customer currentCustomer = customerMapper.getByCustomerId(id);
        if (currentCustomer == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        currentCustomer.setRealname(customer.getRealname());
        currentCustomer.setSex(customer.getSex());
        currentCustomer.setType(customer.getType());
        currentCustomer.setTel(customer.getTel());
        currentCustomer.setEmail(customer.getEmail());
        currentCustomer.setAge(customer.getAge());
        currentCustomer.setProvince(customer.getProvince());
        currentCustomer.setCity(customer.getCity());
        currentCustomer.setCounty(customer.getCounty());
        currentCustomer.setAddress(customer.getAddress());
        customerMapper.updateCustomer(currentCustomer);
        return customerMapper.getByCustomerId(id);
    }

    @Override
    public Boolean deleteCustomer(String ids) {
        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());
            String delIds = del_ids.toString();
            List<Integer> userIds = customerMapper.getUserIds(UserTypeEnum.CUSTOMER.getCode(),delIds.substring(1,delIds.length()-1));
            String del_userIds = userIds.toString();
            userMapper.deleteUserBatch(del_userIds.substring(1,del_userIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            Integer user_id = customerMapper.getUserId(id);
            userMapper.deleteUser(user_id);
        }
        return true;
    }

    @Override
    public Repair createRepair(Integer robert_id, String description) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Robert robert = robertMapper.getRobertById(robert_id);
        Integer userId =  userMapper.getUserIdByUsername(username);
        if (userId.equals(robert.getUser_id())){
            Repair currentRepair =  repairMapper.getRepairByRobertId(robert_id);
            if (currentRepair !=null){ //维修记录不为空的话，获取维修状态，如已完成维修，则需要重新创建维修表
                if (currentRepair.getStatus().equals(RepairStatusEnum.REPAIR_DONE.getCode())){
                    return create(robert_id,description);
                } else if (currentRepair.getStatus().equals(RepairStatusEnum.REPAIR_IS_FAIL.getCode())){
                    throw new BusinessException(ResultCode.REPAIR_IS_ERROR);
                } else {
                    currentRepair.setDescription(description);
                    currentRepair.setTime(new Date());
                    repairMapper.updateRepair(currentRepair);
                    return repairMapper.getRepairByRobertId(robert_id);
                }
            }else {
                return create(robert_id,description);
            }
        }else {
            throw new BusinessException(ResultCode.ROBERT_DATA_IS_WRONG);
        }

    }

    public Repair create(Integer robert_id, String description){
        Repair repair = new Repair();
        repair.setRobert_id(robert_id);
        repair.setDescription(description);
        repair.setTime(new Date());
        repairMapper.insert(repair);
        return repairMapper.getRepairByRobertId(robert_id);
    }


    @Override
    public List<RepairDTO> getMyRepairList(Integer status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("username = {}", username);
        User user = userMapper.findByUsername(username);
        List<Robert> roberts = customerMapper.getRobertsByCustomerId(user.getId());
        List<RepairDTO> repairList = new ArrayList<>();
        for (Robert robert : roberts){
            List<RepairDTO> repairDTO = repairMapper.getRepairByTechnologyId(status,robert.getId());
            repairList.addAll(repairDTO);
        }
        return repairList;
    }

    @Override
    public List<CustomerDTO> getUserIdList() {
//        List<CustomerDTO> customerList = userMapper.getAllCustomerDTO();

        List<Customer> customerList = customerMapper.getAllCustomers();
        List<CustomerDTO> customerDTOS = customerList.stream()
                .map(customer -> userMapper.getCustomerDTOByUsername(customer.getUsername()))
                .collect(Collectors.toList());

        return customerDTOS;
//        return customerList;
    }
}
