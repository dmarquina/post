package com.badenblog.post.repository;

import com.badenblog.post.model.entity.HomePostEntity;
import io.reactivex.Single;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends RxJava2CrudRepository<HomePostEntity,UUID>{
    @Query("select * from home_post where id=?0")
    Single<HomePostEntity> findByIdPost(UUID id);
}