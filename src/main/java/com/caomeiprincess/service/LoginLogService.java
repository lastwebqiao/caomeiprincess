package com.caomeiprincess.service;

import com.caomeiprincess.common.service.BaseService;
import com.caomeiprincess.entity.LoginLog;

public interface LoginLogService extends BaseService<LoginLog> {
   void saveLog(LoginLog log);
}
