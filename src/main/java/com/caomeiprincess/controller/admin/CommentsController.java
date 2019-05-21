package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.QueryPage;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.entity.Comments;
import com.caomeiprincess.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public ResponseCode deleteComments(List<Long> ids){
        commentsService.deleteComments(ids);
        return ResponseCode.success();
    }
}
