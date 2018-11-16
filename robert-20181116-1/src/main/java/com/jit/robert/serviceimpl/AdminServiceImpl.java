package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Admin;
import com.jit.robert.domain.Expert;
import com.jit.robert.domain.Role;
import com.jit.robert.domain.User;
import com.jit.robert.dto.AdminDTO;
import com.jit.robert.mapper.AdminMapper;
import com.jit.robert.mapper.UserMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.AdminService;
import com.jit.robert.util.StringUtil;
import com.jit.robert.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMapper userMapper;
    @Value("${image.url}")
    private String url;
    @Override
    public Admin create(Admin admin) {
        if (userMapper.findByUsername(admin.getUsername()) != null){
            throw  new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        //1、注册管理人员信息
        User user = UserUtil.RegisterUser(admin.getUsername());
        userMapper.insert(user);
        //2、设置角色
        User currentUser = userMapper.findByUsername(admin.getUsername());
        userMapper.insertUserRole(currentUser.getId(),2);
        //3、插入管理人员列表
        adminMapper.insert(admin);
        return adminMapper.getByUsername(admin.getUsername());
    }

    @Override
    public PageVO<AdminDTO> getAllAdmins(PageQO pageQO) {
        Page<AdminDTO> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<AdminDTO> adminDTOList = adminMapper.getAllAdmins();
        return PageVO.build(page);
    }

    @Override
    public Admin updateUserPower(Integer status, String username) {
        Integer user_id = userMapper.getUserIdByUsername(username);
        Admin admin = adminMapper.getByUsername(username);
        Integer role_id = userMapper.getRoleIdByUserId(user_id);
        if (!admin.getStatus().equals(status) && role_id.equals(2)){
            userMapper.updateUserRole(user_id,6);
        } else if (!admin.getStatus().equals(status) && role_id.equals(6)) {
            userMapper.updateUserRole(user_id,2);
        }
        adminMapper.updateUserPower(status, username);
        return adminMapper.getByUsername(username);
    }

    @Override
    public Admin updateAdminInfo(String username,String department, String remark) {
        Admin admin = adminMapper.getByUsername(username);
        admin.setDepartment(department);
        admin.setUsername(username);
        admin.setRemark(remark);
        int flag = adminMapper.updateAdminInfo(admin);
        return adminMapper.getByUsername(username);
    }

    @Override
    public Boolean updatePassword(String username) {

        Admin admin = adminMapper.getByUsername(username);
        if (admin.getStatus()==0){

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newPassword = "123456";
            int flag = userMapper.updatePassword(passwordEncoder.encode(newPassword),username);
            if (flag>0){
                return true;
            }else{
                throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
            }
        }else {
            throw new BusinessException(ResultCode.PERMISSION_NOT_DENYED);
        }
    }

    @Override
    public AdminDTO getAdminInfo(Integer id) {

        AdminDTO admin = adminMapper.getAdminById(id);
        changeImage(admin);
        return admin;
    }
    private void changeImages(List<AdminDTO> list){
        for (int i=0;i<list.size();i++){
            changeImage(list.get(i));
        }
    }

    private void changeImage(AdminDTO obj){
        if (obj.getImage()!=null){
            obj.setImage(url+obj.getImage());
        }
    }
}
