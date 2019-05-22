package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.annotation.Log;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.QueryPage;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.entity.Comments;
import com.caomeiprincess.exception.GlobalException;
import com.caomeiprincess.service.CommentsService;
import com.caomeiprincess.utils.AddressUtil;
import com.caomeiprincess.utils.IPUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class CommentsController extends BaseController {
    @RequestMapping("admin/comments")
    public String pageComments(Model model){
        init(model);
        return "admin/page/comments";
    }

    @Autowired
    private CommentsService commentsService;
    @BodyMapping(value = "comments/findByPage",method = RequestMethod.POST)
    public ResponseCode findComments(QueryPage queryPage,Comments comments){
        return ResponseCode.success(selectByPageNumSize(queryPage,() -> commentsService.findByPage(comments)));
    }

    @BodyMapping("comments/delete")
    @Log("删除评论")
    public ResponseCode deleteComments(List<Long> ids){
        commentsService.deleteComments(ids);
        return ResponseCode.success();
    }

    @BodyMapping("comments/save")
    @Log("新增评论")
    public ResponseCode save(@RequestBody Comments comments, HttpServletRequest request){
        try {
            String ip = IPUtil.getIpAddr(request);
            comments.setTime(new Date());
            comments.setIp(ip);
            comments.setAddress(AddressUtil.getAddress(ip));
            String header = request.getHeader("User-Agent");
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            Browser browser = userAgent.getBrowser();
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            comments.setDevice(browser.getName() + "," + operatingSystem.getName());
            commentsService.save(comments);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
