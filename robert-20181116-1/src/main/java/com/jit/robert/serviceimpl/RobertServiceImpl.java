package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Customer;
import com.jit.robert.domain.Robert;
import com.jit.robert.dto.RobertDTO;
import com.jit.robert.domain.User;
import com.jit.robert.mapper.CustomerMapper;
import com.jit.robert.mapper.RobertMapper;
import com.jit.robert.mapper.RoleMapper;
import com.jit.robert.mapper.UserMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.RobertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RobertServiceImpl implements RobertService {
    @Autowired
    private RobertMapper robertMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 新增机器人
     * @param robert
     * @return
     */
    @Override
    public RobertDTO insertRobert(Robert robert) {
        Robert isExist = robertMapper.getRobertByNumber(robert.getNumber());
        if (isExist!= null){
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = roleMapper.getRoleByUsername(username);
        if (role.equals("ROLE_CUSTOMER")){
            User user = userMapper.findByUsername(username);
            if (user!=null){
                robert.setUser_id(user.getId());
                robertMapper.insert(robert);
            }else {
                throw new BusinessException(ResultCode.DATA_IS_WRONG);
            }
        }else {
            robertMapper.insert(robert);
        }
        return robertMapper.getRobertInfoById(robert.getId());
    }

    /**
     * 管理员为客户分配机器人
     * @param robert_id
     * @param user_id
     * @return
     */
    @Override
    public Robert assignRobertByAdmin(Integer robert_id, Integer user_id) {

        Robert robert = new Robert();
        Customer customer = customerMapper.getByCustomerId(user_id);
        User user = userMapper.findByUsername(customer.getUsername());
        robert.setUser_id(user.getId());
        robert.setId(robert_id);
        int flag = robertMapper.assignRobertByAdmin(robert);
        if (flag>0){
            return robertMapper.getRobertById(robert_id);
        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }


    /**
     * 更新机器人信息
     * @param robert
     * @param id
     * @return
     */
    @Override
    public RobertDTO updateRobert(Robert robert, Integer id) {
        robert.setId(id);
        int flag = robertMapper.updateRobert(robert);
        if (flag>0){
            return robertMapper.getRobertInfoById(id);
        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }

    /**
     * 删除机器人
     * @param id
     * @return
     */
    @Override
    public Boolean deleteRobert(Integer id) {
        int flag = robertMapper.deleteRobert(id);
        if (flag>0){
            return true;
        }else {
            throw new BusinessException(ResultCode.DATABASE_DELETE_ERROR);
        }
    }

    /**
     * 根据客户获取所有机器人
     * @param customer_id
     * @return
     */
    @Override
    public PageVO<RobertDTO> getAllRobertsByCustomer(Integer customer_id, PageQO pageQO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.findByUsername(username);
        log.info("username = {},userId = {}",username,user.getId());

            Integer user_id = customerMapper.getUserId(customer_id);
            log.info("user_id={}",user_id);
            Page<RobertDTO> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            List<RobertDTO> roberts = robertMapper.getAllRobertsByCustemer(user_id);
            return PageVO.build(page);


    }

    /**
     * 根据类型获取所有机器人
     * @param type
     * @return
     */
    @Override
    public PageVO<RobertDTO> getAllRobertsByType(String type, PageQO pageQO) {
        Page<RobertDTO> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
       List<RobertDTO> roberts = robertMapper.getAllRobertsByType(type);
       return PageVO.build(page);
    }

    /**
     * 管理员获取所有机器人
     * @return
     */
    @Override
    public PageVO<RobertDTO> getAllRoberts(PageQO pageQO) {
        Page<RobertDTO> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<RobertDTO> roberts = robertMapper.getAllRoberts();
        return PageVO.build(page);
    }

    @Override
    public List<String> getAllRobertType() {
        return robertMapper.getAllRobertType();
    }

    @Override
    public List<Robert> getAllRobertsWithoutPage() {
        return robertMapper.getAllRobertsWithoutPage();
    }
}
