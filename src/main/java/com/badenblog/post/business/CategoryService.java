package com.badenblog.post.business;

import com.badenblog.post.model.entity.CategoryEntity;
import com.badenblog.post.model.json.CategoryIdsFilterRequest;
import io.reactivex.Observable;

import java.util.Map;


public interface CategoryService {
    Observable<CategoryEntity> findAll();

    Map findByIdCategory(CategoryIdsFilterRequest categoryIds, String pageState);
}
