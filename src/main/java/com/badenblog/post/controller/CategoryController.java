package com.badenblog.post.controller;

import com.badenblog.post.business.CategoryService;
import com.badenblog.post.model.entity.CategoryEntity;
import com.badenblog.post.model.entity.PostByCategoryEntity;
import com.badenblog.post.model.json.post.CategoryIdsFilterRequest;
import com.badenblog.post.model.json.post.response.HomePostResponse;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/", method = RequestMethod.GET )
    public Observable<CategoryEntity> findAll() {
        return categoryService.findAll();
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST )
    public Observable<HomePostResponse> findPostByCategory(@RequestBody CategoryIdsFilterRequest categoryIdsFilterRequest) {
        return categoryService.findByIdCategory(categoryIdsFilterRequest);
    }
}
