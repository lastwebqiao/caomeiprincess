package com.caomeiprincess.service.impl;

import com.caomeiprincess.common.service.impl.BaseServiceImpl;
import com.caomeiprincess.entity.LoginLog;
import com.caomeiprincess.mapper.LoginLogMapper;
import com.caomeiprincess.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLog> implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Override
    public void saveLog(LoginLog log) {
        loginLogMapper.insert(log);
    }
}
