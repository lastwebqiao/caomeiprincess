package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.Log;
import com.caomeiprincess.common.CommonHolder;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.entity.LoginLog;
import com.caomeiprincess.service.LoginLogService;
import com.caomeiprincess.utils.AddressUtil;
import com.caomeiprincess.utils.HttpContextUtil;
import com.caomeiprincess.utils.IPUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登入controller
 */
@Controller
public class LoginController extends BaseController {
    /**
     * 跳转登入页面
     * @return
     */
    @RequestMapping("login")
    public String login(){
        return "admin/login";
    }

    /**
     * 执行登入操作
     * @param model
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Autowired
    private LoginLogService loginLogService;
    @RequestMapping("admin/login")
    @ResponseBody
    @Log("登入")
    public ResponseCode loginIn(Model model,
                                @RequestParam(value = "username", required = false) String username,
                                @RequestParam(value = "password", required = false) String password,
                                @RequestParam(value = "code", required = false) String code,
                                @RequestParam(value = "remember", required = false) String remember){
        if (username != null && password != null && code!=null) {
            try {
                Assert.isTrue(code.matches("[0-9]{6}"),"安全码格式错误(6位数字)");
                CommonHolder.secCode.set(Integer.parseInt(code));
                Subject subject = getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                if (remember != null) {
                    if (remember.equals("true")) {
                        //说明选择了记住我
                        token.setRememberMe(true);
                    } else {
                        token.setRememberMe(false);
                    }
                } else {
                    token.setRememberMe(false);
                }

                subject.login(token);

                //记录登录日志
                LoginLog log = new LoginLog();
                //获取HTTP请求
                HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
                String ip = IPUtil.getIpAddr(request);
                log.setIp(ip);
                log.setUsername(super.getCurrentUser().getUsername());
                log.setLocation(AddressUtil.getAddress(ip));
                log.setCreateTime(new Date());
                String header = request.getHeader("User-Agent");
                UserAgent userAgent = UserAgent.parseUserAgentString(header);
                Browser browser = userAgent.getBrowser();
                OperatingSystem operatingSystem = userAgent.getOperatingSystem();
                log.setDevice(browser.getName() + " -- " + operatingSystem.getName());
                loginLogService.saveLog(log);

                model.addAttribute("username", getSubject().getPrincipal());
                return ResponseCode.success();
            } catch (Exception e) {
                //e.printStackTrace();
                ResponseCode responseCode = ResponseCode.error();
                responseCode.setMsg("登入失败！");
                return responseCode;
            }
        } else {
            ResponseCode responseCode = ResponseCode.error();
            responseCode.setMsg("登入失败！");
            return responseCode;
        }
    }

    /**
     * 跳转首页
     * @return
     */
    @RequestMapping("admin")
    public String admin(Model model){
        model.addAttribute("user", this.getCurrentUser());
        return "admin/index";
    }
    /**
     * 退出登入
     */
    @RequestMapping("/admin/logout")
    public String logout(){
        getSubject().logout();
        return "redirect:/login";
    }
}
