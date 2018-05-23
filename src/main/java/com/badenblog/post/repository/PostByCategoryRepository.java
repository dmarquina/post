package com.badenblog.post.repository;

import com.badenblog.post.model.entity.PostByCategoryEntity;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface PostByCategoryRepository extends RxJava2CrudRepository<PostByCategoryEntity,UUID>{
    @Query("select * from post_by_category where id_category=?0 ")
    Maybe <PostByCategoryEntity> findByIdCategory(UUID id);

    @Query("select * from post_by_category where id_category IN ?0 ")
    Flowable<PostByCategoryEntity> findAllByIdCategory(Set<UUID> ids);
}