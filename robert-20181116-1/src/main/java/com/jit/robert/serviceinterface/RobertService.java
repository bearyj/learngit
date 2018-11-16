package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Robert;
import com.jit.robert.dto.RobertDTO;

import java.util.List;

public interface RobertService {
    RobertDTO insertRobert(Robert robert);
    Robert assignRobertByAdmin(Integer robert_id,Integer user_id);
    Boolean deleteRobert(Integer id);
    RobertDTO updateRobert(Robert robert,Integer id);
    PageVO<RobertDTO> getAllRobertsByCustomer(Integer customer_id, PageQO pageQO);
    PageVO<RobertDTO> getAllRobertsByType(String type,PageQO pageQO);
    PageVO<RobertDTO> getAllRoberts(PageQO pageQO);
    List<String> getAllRobertType();
    List<Robert> getAllRobertsWithoutPage();
}
