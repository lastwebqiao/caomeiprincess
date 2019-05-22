package com.caomeiprincess.service;

import com.caomeiprincess.common.service.BaseService;
import com.caomeiprincess.entity.Links;

import java.util.List;

public interface LinksService extends BaseService<Links> {
    int findAllCount();

    List<Links> findByPage(Links link);
}
