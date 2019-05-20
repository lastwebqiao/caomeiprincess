package com.caomeiprincess.service;

import com.caomeiprincess.common.service.BaseService;
import com.caomeiprincess.entity.SysLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

public interface LogService extends BaseService<SysLog> {
    @Async
    void saveLog(ProceedingJoinPoint proceedingJoinPoint, SysLog log) throws JsonProcessingException;
}
