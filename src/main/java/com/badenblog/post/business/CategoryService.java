package com.badenblog.post.business;

import com.badenblog.post.model.entity.CategoryEntity;
import com.badenblog.post.model.json.post.CategoryIdsFilterRequest;
import com.badenblog.post.model.json.post.response.HomePostResponse;
import io.reactivex.Observable;

import java.util.Map;


public interface CategoryService {
    Observable<CategoryEntity> findAll();

    Map findByIdCategory(CategoryIdsFilterRequest categoryIds, String pageState);
}
