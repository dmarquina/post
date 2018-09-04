package com.badenblog.post.business;

import com.badenblog.post.model.entity.HomePostEntity;
import com.badenblog.post.model.json.NewPostRequest;
import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.Map;


public interface PostService {
    Map findAll(String pageState);
    Completable createPost(NewPostRequest newPostRequest);
    Single<HomePostEntity> detailPost(String id);
}
