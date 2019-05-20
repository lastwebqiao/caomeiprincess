package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.service.ArticleService;
import com.caomeiprincess.service.CommentsService;
import com.caomeiprincess.service.LinksService;
import com.caomeiprincess.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @BodyMapping(value = "article/findAllCount")
    public ResponseCode articleCount(){
        return ResponseCode.success(articleService.findAllCount());
    }

    @BodyMapping("article/findAll")
    public ResponseCode articleFindAll(){
        return ResponseCode.success(articleService.findAll());
    }

    @Autowired
    private TagService tagService;
    @BodyMapping(value = "tags/findAllCount")
    public ResponseCode tagesCount(){
        return ResponseCode.success(tagService.findAllCount());
    }

    @Autowired
    private CommentsService commentsService;
    @BodyMapping("comments/findAllCount")
    public ResponseCode commentsCount(){
        return ResponseCode.success(commentsService.findAllCount());
    }

    @BodyMapping("comments/findAll")
    public ResponseCode commentsFindAll(){
        return ResponseCode.success(commentsService.findAll());
    }

    @Autowired
    private LinksService linksService;
    @BodyMapping("links/findAllCount")
    public ResponseCode linksCount(){
        return ResponseCode.success(linksService.findAllCount());
    }
}
