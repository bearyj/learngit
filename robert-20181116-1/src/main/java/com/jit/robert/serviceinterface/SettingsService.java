package com.jit.robert.serviceinterface;

import com.jit.robert.domain.Settings;
import com.jit.robert.dto.SettingsDTO;

import java.util.List;

public interface SettingsService {
    SettingsDTO insertSettings(Settings settings);
    Boolean deleteSettings(Integer id);
    SettingsDTO updateSettings(Settings settings, Integer id);
    SettingsDTO getAllSettingsByPound(Integer pound_id);
    List<SettingsDTO> getAllSettingsByUser();
}
