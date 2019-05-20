package com.caomeiprincess.controller.admin;

import com.caomeiprincess.annotation.BodyMapping;
import com.caomeiprincess.dto.ResponseCode;
import com.caomeiprincess.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @BodyMapping("category/findAll")
    public ResponseCode findAll(){
        return ResponseCode.success(categoryService.findAll());
    }
}
