package com.badenblog.post.controller;

import com.badenblog.post.business.CategoryService;
import com.badenblog.post.model.entity.CategoryEntity;
import com.badenblog.post.model.entity.PostByCategoryEntity;
import com.badenblog.post.model.json.post.CategoryIdsFilterRequest;
import com.badenblog.post.model.json.post.response.HomePostResponse;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

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
    public Observable<HomePostResponse> findPostByCategory(HttpServletResponse response,
                                                           @RequestParam(required = false) String pageState,
                                                           @RequestBody CategoryIdsFilterRequest categoryIdsFilterRequest) {
        Map serviceResponse = categoryService.findByIdCategory(categoryIdsFilterRequest,pageState);
        Optional.ofNullable(serviceResponse.get("pageState"))
                .ifPresent(p -> response.setHeader("pageState", p.toString()));
        return (Observable<HomePostResponse>) serviceResponse.get("data");
    }
}
