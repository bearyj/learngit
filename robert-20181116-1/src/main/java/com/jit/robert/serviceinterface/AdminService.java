package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Admin;
import com.jit.robert.dto.AdminDTO;

public interface AdminService {

    Admin create(Admin admin);

    PageVO<AdminDTO> getAllAdmins(PageQO pageQO);

    Admin updateUserPower(Integer status, String username);

    Admin updateAdminInfo(String username,String department,String remark);

    Boolean updatePassword(String username);

    AdminDTO getAdminInfo(Integer id);

}
