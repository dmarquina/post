package com.badenblog.post.repository;

import com.badenblog.post.model.entity.ScheduleEntity;
import io.reactivex.Flowable;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScheduleRepository extends RxJava2CrudRepository<ScheduleEntity,String>{
    Flowable<ScheduleEntity> findAllByUserIdOrderByApplicationDateDesc(String userId);
}