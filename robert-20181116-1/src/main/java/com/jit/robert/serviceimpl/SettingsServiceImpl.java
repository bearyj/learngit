package com.jit.robert.serviceimpl;

import com.jit.robert.domain.Customer;
import com.jit.robert.domain.Settings;
import com.jit.robert.domain.User;
import com.jit.robert.dto.SettingsDTO;
import com.jit.robert.mapper.CustomerMapper;
import com.jit.robert.mapper.RobertMapper;
import com.jit.robert.mapper.SettingsMapper;
import com.jit.robert.mapper.UserMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.SettingsService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsServiceImpl implements SettingsService{
    @Autowired
    private SettingsMapper settingsMapper;

    @Autowired
    private RobertMapper robertMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 插入配置
     * @param settings
     * @return
     */
    @Override
    public SettingsDTO insertSettings(Settings settings) {
        //一个塘口只能有一个settings配置
        SettingsDTO settingsByPound = settingsMapper.getSettingsByPound(settings.getPound_id());
        if (settingsByPound!=null){
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        int flag = settingsMapper.insert(settings);
        if (flag>0){
            return settingsMapper.getSettingsByPound(settings.getPound_id());
        }else throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
    }

    /**
     * 根据id删除配置
     * @param id
     * @return
     */
    @Override
    public Boolean deleteSettings(Integer id) {
        int flag = settingsMapper.delete(id);
        if (flag>0){
            return true;
        }else {
            throw new BusinessException(ResultCode.DATABASE_DELETE_ERROR);
        }
    }

    /**
     * 根据id更新配置
     * @param settings
     * @param id
     * @return
     */
    @Override
    public SettingsDTO updateSettings(Settings settings, Integer id) {
        SettingsDTO settingsByPound = settingsMapper.getSettingsByPound(settings.getPound_id());
        if (settingsByPound==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        settings.setId(id);
        int flag = settingsMapper.update(settings);
        if (flag>0){
            return settingsMapper.getSettingsById(id);
        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }

    /**
     * 根据塘口获取配置
     * @param pound_id
     * @return
     */
    @Override
    public SettingsDTO getAllSettingsByPound(Integer pound_id) {
        SettingsDTO settings = settingsMapper.getSettingsByPound(pound_id);
        return settings;
    }

    @Override
    public List<SettingsDTO> getAllSettingsByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Customer customer = customerMapper.getByUsername(username);
        User user = userMapper.findByUsername(username);
        List<Integer> robertIds = robertMapper.getRobertIdsByCustomer(user.getId());
        List<SettingsDTO> settingsList = new ArrayList<>();
        for (Integer robertId : robertIds){
            SettingsDTO settings = settingsMapper.getSettingsByRobert(robertId);
            if (settings !=null){
                settingsList.add(settings);
            }

        }
        return settingsList;
    }
}
