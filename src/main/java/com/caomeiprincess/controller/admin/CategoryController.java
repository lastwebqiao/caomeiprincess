package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.annotation.Log;
import com.caomeiprincess.controller.BaseController;
import com.caomeiprincess.dto.QueryPage;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.entity.Category;
import com.caomeiprincess.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;
    @BodyMapping("category/findAll")
    public ResponseCode findAll(){
        return ResponseCode.success(categoryService.findAll());
    }

    @RequestMapping("admin/category")
    public String pageCategory(Model model){
        init(model);
        return "admin/page/category";
    }

    @BodyMapping("category/findByPage")
    public ResponseCode findByPage(QueryPage queryPage){
        return ResponseCode.success(selectByPageNumSize(queryPage,()->categoryService.findAll()));
    }

    @BodyMapping("category/delete")
    @Log("删除分类")
    public ResponseCode delete(@RequestBody List<Long> ids){
        categoryService.delete(ids);
        return ResponseCode.success();
    }
    @BodyMapping("category/save")
    @Log("新增分类")
    public ResponseCode save(@RequestBody Category category){
        categoryService.save(category);
        return ResponseCode.success();
    }

    @BodyMapping("category/update")
    @Log("修改分类")
    public ResponseCode update(@RequestBody Category category){
        categoryService.update(category);
        return ResponseCode.success();
    }

    @BodyMapping("category/findById")
    public ResponseCode findById(@RequestParam Long id){
        return ResponseCode.success(categoryService.findById(id));
    }
}
