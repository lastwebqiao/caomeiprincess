package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.annotation.Log;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.QueryPage;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.entity.Links;
import com.caomeiprincess.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LinksController extends BaseController {
    @RequestMapping("admin/links")
    public String pageLinks(Model model){
        init(model);
        return "admin/page/links";
    }

    @Autowired
    private LinksService linksService;
    @BodyMapping("links/findByPage")
    public ResponseCode findByPage(QueryPage queryPage, Links link){
       return ResponseCode.success(super.selectByPageNumSize(queryPage, () -> linksService.findByPage(link)));
    }

    @BodyMapping("links/save")
    @Log("新增友链")
    public ResponseCode save(@RequestBody Links links){
        linksService.save(links);
        return ResponseCode.success();
    }
    @BodyMapping("links/update")
    @Log("修改又链")
    public ResponseCode update(Links links){
        linksService.updateAll(links);
        return ResponseCode.success();
    }
    @BodyMapping("links/delete")
    @Log("删除友链")
    public ResponseCode delete(@RequestBody List<Long> ids){
        linksService.batchDelete(ids,"id",Links.class);
        return ResponseCode.success();
    }
    @BodyMapping("links/findById")
    public ResponseCode findById(Long id){
        linksService.selectByKey(id);
        return ResponseCode.success();
    }
}
