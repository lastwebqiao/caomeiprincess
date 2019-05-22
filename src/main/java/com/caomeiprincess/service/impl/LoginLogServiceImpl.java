package com.caomeiprincess.service.impl;

import com.caomeiprincess.common.service.impl.BaseServiceImpl;
import com.caomeiprincess.entity.LoginLog;
import com.caomeiprincess.mapper.LoginLogMapper;
import com.caomeiprincess.service.LoginLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLog> implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Override
    public void saveLog(LoginLog log) {
        loginLogMapper.insert(log);
    }

    @Override
    public List<LoginLog> findByPage(LoginLog loginLog) {
        try {
            Example example = new Example(LoginLog.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(loginLog.getUsername())) {
                criteria.andLike("username", "%"+loginLog.getUsername()+"%");
            }

            if (StringUtils.isNotBlank(loginLog.getLocation())) {
                criteria.andCondition("location=", loginLog.getLocation());
            }

            if (StringUtils.isNotBlank(loginLog.getFiledTime())) {
                String[] split = loginLog.getFiledTime().split(",");
                criteria.andCondition("data_format(CREATE_TIME, '%Y-%m-%d') >=", split[0]);
                criteria.andCondition("data_format(CREATE_TIME, '%Y-%m-%d') <=", split[1]);
            }
            example.setOrderByClause("create_time desc");
            return this.selectByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
