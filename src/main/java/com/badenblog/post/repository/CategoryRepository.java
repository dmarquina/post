package com.badenblog.post.repository;

import com.badenblog.post.model.entity.CategoryEntity;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends RxJava2CrudRepository<CategoryEntity,String> {

}