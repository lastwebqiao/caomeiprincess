package com.caomeiprincess.service;

import com.caomeiprincess.common.service.BaseService;
import com.caomeiprincess.entity.Setting;
import com.caomeiprincess.entity.User;

public interface UserService extends BaseService<Setting> {

    Setting findSetting();

    User findByName(String username);
}
