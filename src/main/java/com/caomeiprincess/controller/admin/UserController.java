package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.annotation.Log;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.entity.Setting;
import com.caomeiprincess.entity.User;
import com.caomeiprincess.exception.GlobalException;
import com.caomeiprincess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController extends BaseController {

    @RequestMapping("admin/user")
    public String pageUser(Model model){
        init(model);
        return "admin/page/user";
    }

    @Autowired
    private UserService userService;

    @BodyMapping("admin/info")
    public ResponseCode info(){
        return ResponseCode.success(this.getCurrentUser());
    }

    @BodyMapping(value = "admin/findById")
    public ResponseCode findById(@RequestParam("id") Long id) {
        return ResponseCode.success(userService.selectByKey(id));
    }

    @BodyMapping(value = "user/save")
    @Log("新增用户")
    public ResponseCode save(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @BodyMapping("user/update")
    @Log("更新用户")
    public ResponseCode update(@RequestBody User user) {
        try {
            userService.updateAll(user);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @BodyMapping(value = "user/delete")
    @Log("删除用户")
    public ResponseCode delete(@RequestBody List<Long> ids) {
        try {
            userService.delete(ids);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @RequestMapping("admin/setting")
    public String pageSetting(Model model){
        init(model);
        return "admin/page/setting";
    }

    @BodyMapping("user/getSetting")
    public ResponseCode getSetting() {
        return ResponseCode.success(userService.findSetting());
    }

    @BodyMapping("user/updateSetting")
    @Log("更新系统设置")
    public ResponseCode updateSetting(@RequestBody Setting setting) {
        try {
            userService.updateSetting(setting);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
