package com.caomeiprincess.service.impl;

import com.caomeiprincess.common.service.impl.BaseServiceImpl;
import com.caomeiprincess.entity.Setting;
import com.caomeiprincess.entity.User;
import com.caomeiprincess.mapper.SettingMapper;
import com.caomeiprincess.mapper.UserMapper;
import com.caomeiprincess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<Setting> implements UserService {

    @Autowired
    private SettingMapper settingMapper;
    @Override
    public Setting findSetting() {
        return settingMapper.selectAll().get(0);
    }

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByName(String username) {
        if (!username.isEmpty()) {
            User user = new User();
            user.setUsername(username);
            List userlist = userMapper.select(user);
            return userlist.size()>0?userMapper.select(user).get(0):new User();
        } else {
            return new User();
        }
    }

}
