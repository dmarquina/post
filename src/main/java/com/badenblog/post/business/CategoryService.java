package com.badenblog.post.business;

import com.badenblog.post.model.entity.CategoryEntity;
import com.badenblog.post.model.json.post.CategoryIdsFilterRequest;
import com.badenblog.post.model.json.post.response.HomePostResponse;
import io.reactivex.Observable;


public interface CategoryService {
    Observable<CategoryEntity> findAll();

    Observable<HomePostResponse> findByIdCategory(CategoryIdsFilterRequest categoryIds);
}
