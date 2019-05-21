package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.ResponseCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LinksController extends BaseController {
    @RequestMapping("admin/links")
    public String pageLinks(Model model){
        init(model);
        return "admin/page/links";
    }

    @BodyMapping("links/findByPage")
    public ResponseCode findByPage(){
        //Todo
       return ResponseCode.success();
    }
}
