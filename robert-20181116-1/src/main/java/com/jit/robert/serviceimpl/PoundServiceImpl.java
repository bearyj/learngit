package com.jit.robert.serviceimpl;

import com.jit.robert.domain.Pound;
import com.jit.robert.mapper.PoundMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.PoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoundServiceImpl implements PoundService {
    @Autowired
    private PoundMapper poundMapper;

    /**
     * 插入塘口数据
     * @param pound
     * @return
     */
    @Override
    public Pound insertPound(Pound pound) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        pound.setUsername(username);

        int flag = poundMapper.insert(pound);
        if (flag>0){
            return pound;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    /**
     * 根据id删除塘口
     * @param id
     * @return
     */
    @Override
    public Boolean deletePoundById(Integer id) {
        int flag = poundMapper.delete(id);
        if (flag>0){
            return true;
        }else {
            throw new BusinessException(ResultCode.DATABASE_DELETE_ERROR);
        }
    }

    /**
     * 更新塘口数据
     * @param id
     * @param pound
     * @return
     */
    @Override
    public Pound updatePoundById(Integer id, Pound pound) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        pound.setUsername(username);
        pound.setId(id);

        Pound isExist = poundMapper.getPoundById(id);
        if (isExist == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        int flag = poundMapper.update(pound);
        if (flag>0){
            return poundMapper.getPoundById(id);
        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }

    /**
     * 根据当前登录用户获取所有塘口
     * @return
     */
    @Override
    public List<Pound> getAllPoundsByCustomer() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Pound> poundList = poundMapper.getAllPoundsByCustomer(username);
        return poundList;
    }
}
