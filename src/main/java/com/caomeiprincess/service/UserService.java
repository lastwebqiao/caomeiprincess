package com.caomeiprincess.service;

import com.caomeiprincess.common.service.BaseService;
import com.caomeiprincess.entity.Setting;
import com.caomeiprincess.entity.User;

public interface UserService extends BaseService<User> {

    Setting findSetting();

    User findByName(String username);

    void updateSetting(Setting setting);
}
