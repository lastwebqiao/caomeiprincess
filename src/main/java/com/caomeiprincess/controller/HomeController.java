package com.caomeiprincess.controller;

import com.alibaba.fastjson.JSONArray;
import com.caomeiprincess.entity.Setting;
import com.caomeiprincess.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 首页控制器
 *
 * @auther TyCoding
 * @date 2018/9/28
 */
@Controller
public class HomeController {


    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        initCommon(model);
        initIndex(1, model);
        return "site/index";
    }
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentsService commentsService;
    /**
     * 初始化公用数据
     *
     * @param model
     */
    private void initCommon(Model model) {
        model.addAttribute("newArticle", articleService.findAll());
        model.addAttribute("newComment", commentsService.findAll());
        Setting setting = userService.findSetting();
        setting.setSiteLinks(JSONArray.parseArray((String) setting.getSiteLinks()));
        setting.setSiteDonation(JSONArray.parseArray((String) setting.getSiteDonation()));
        model.addAttribute("setting", setting);
    }

    /**
     * 初始化首页数据
     *
     * @param pageCode
     * @param model
     */
    private void initIndex(Integer pageCode, Model model) {
        //分页文章数据
        Map<String, Object> map = articleService.findByPageForSite(pageCode, 6);
        map.put("total", (long) Math.ceil(((Long) map.get("total")).doubleValue() / (double) 6));
        //格式：[{...}, {...}, {...}]
        model.addAttribute("list", map);
        model.addAttribute("pageCode", pageCode);
    }

    @Autowired
    private TagService tagService;
    @GetMapping("/tags")
    public String tags(Model model) {
        model.addAttribute("list", tagService.findAll());
        initCommon(model);
        return "site/page/tags/index";
    }

    @Autowired
    private ArticleTagsService articleTagService;

    @GetMapping("/tags/{tag}")
    public String archives(@PathVariable("tag") String tag, Model model) {
        model.addAttribute("list", articleTagService.findByTagName(tag));
        model.addAttribute("tag", tag);
        initCommon(model);
        return "site/page/tags/tag";
    }

    /**
     * 归档页面
     *
     * @return
     */
    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("list", articleService.findArchives());
        initCommon(model);
        return "site/page/archives";
    }

    /**
     * 友情链接
     *
     * @return
     */
    @Autowired
    private LinksService linksService;
    @GetMapping("/links")
    public String link(Model model, @RequestParam(value = "cp", required = false) Integer cp) {
        //加载友情链接数据
        model.addAttribute("list", linksService.selectAll());
        if (cp == null) {
            //查询的第一页评论数据
            cp = 1;
        }
        //三个参数：1.pageCode当前页；2.PageSize默认每页显示6条（大）留言；3.文章ID值；4.sort当前是文章详情页，sort=0。
        //规定：sort=0表示文章详情页的评论信息；sort=1表示友链页的评论信息；sort=2表示关于我页的评论信息
        getPage(commentsService.findCommentsList(cp, 6, 0, 1), model, cp, 1);
        return "site/page/links";
    }

    private void getPage(Map<String, Object> map, Model model, Integer cp, Integer sort) {
        model.addAttribute("count", map.get("total"));
        map.put("total", (long) Math.ceil(((Long) map.get("total")).doubleValue() / (double) 6));
        model.addAttribute("talkList", map);
        model.addAttribute("cp", cp);
        model.addAttribute("sort", sort);
        initCommon(model);
    }
}
