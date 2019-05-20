package com.caomeiprincess.service.impl;

import com.caomeiprincess.common.service.impl.BaseServiceImpl;
import com.caomeiprincess.entity.Links;
import com.caomeiprincess.mapper.LinksMapper;
import com.caomeiprincess.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinksServiceImpl extends BaseServiceImpl<Links> implements LinksService{

    @Autowired
    private LinksMapper linksMapper;
    @Override
    public int findAllCount() {
        return linksMapper.selectCount(new Links());
    }
}
