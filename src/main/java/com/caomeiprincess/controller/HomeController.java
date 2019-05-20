package com.caomeiprincess.controller;

import com.alibaba.fastjson.JSONArray;
import com.caomeiprincess.entity.Setting;
import com.caomeiprincess.service.ArticleService;
import com.caomeiprincess.service.CommentsService;
import com.caomeiprincess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
