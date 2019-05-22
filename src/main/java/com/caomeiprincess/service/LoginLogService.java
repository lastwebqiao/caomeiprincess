package com.caomeiprincess.service;

import com.caomeiprincess.common.service.BaseService;
import com.caomeiprincess.entity.LoginLog;

import java.util.List;

public interface LoginLogService extends BaseService<LoginLog> {
   void saveLog(LoginLog log);

    List<LoginLog> findByPage(LoginLog loginLog);
}
