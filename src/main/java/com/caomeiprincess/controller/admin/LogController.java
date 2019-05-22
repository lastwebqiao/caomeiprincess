package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.annotation.Log;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.QueryPage;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.entity.SysLog;
import com.caomeiprincess.exception.GlobalException;
import com.caomeiprincess.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LogController extends BaseController {
    @RequestMapping("admin/log")
    public String pageLog(Model model){
        init(model);
        return "admin/page/log";
    }

    @Autowired
    private LogService logService;
    @BodyMapping("log/list")
    public ResponseCode list(QueryPage queryPage, SysLog log){

        return ResponseCode.success(selectByPageNumSize(queryPage,()->logService.findByPage(log)));
    }

    @Log("删除系统日志")
    @BodyMapping("log/delete")
    //@RequiresPermissions("log:delete")
    public ResponseCode delete(@RequestBody List<Long> ids) {
        try {
            logService.batchDelete(ids,"id",SysLog.class);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
