package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.annotation.Log;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.QueryPage;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.entity.LoginLog;
import com.caomeiprincess.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginlogController extends BaseController {

    @RequestMapping("admin/loginlog")
    public String pageLoginlog(Model model){
        init(model);
        return "admin/page/loginlog";
    }

    @Autowired
    private LoginLogService loginLogService;
    @BodyMapping("loginlog/list")
    public ResponseCode list(QueryPage queryPage,LoginLog loginLog){
        return ResponseCode.success(selectByPageNumSize(queryPage,()-> loginLogService.findByPage(loginLog)));
    }

    @BodyMapping("loginlog/delete")
    @Log("删除登入日志")
    public ResponseCode delete(@RequestBody List<Long> ids){
        loginLogService.batchDelete(ids,"id",LoginLog.class);
        return ResponseCode.success();
    }
}
