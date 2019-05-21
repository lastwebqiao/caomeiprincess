package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.QueryPage;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.entity.Tags;
import com.caomeiprincess.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagsController extends BaseController {
    @Autowired
    private TagService tagService;
    @BodyMapping("tags/findByPage")
    public ResponseCode findTagsByPage(QueryPage queryPage){
        return ResponseCode.success(selectByPageNumSize(queryPage,()->tagService.findAll()));
    }

    @BodyMapping("tags/delete")
    public ResponseCode delete(@RequestBody List<Long> ids){
        tagService.delete(ids);
        return ResponseCode.success();
    }

    @BodyMapping("tags/save")
    public ResponseCode delete(@RequestBody Tags tags){
        tagService.save(tags);
        return ResponseCode.success();
    }

    @BodyMapping("tags/update")
    public ResponseCode update(@RequestBody Tags tags){
        tagService.update(tags);
        return ResponseCode.success();
    }

    @BodyMapping("tags/findById")
    public ResponseCode findById(@RequestParam("id") Long id){
        return ResponseCode.success(tagService.findById(id));
    }
}
