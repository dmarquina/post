package com.badenblog.post.controller;

import com.badenblog.post.business.PostService;
import com.badenblog.post.model.entity.HomePostEntity;
import com.badenblog.post.model.json.post.NewPostRequest;
import com.badenblog.post.model.json.post.response.HomePostResponse;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Observable<HomePostResponse> getPostFeed(HttpServletResponse response, @RequestParam(required = false) String pageState) {
        Map serviceResponse = postService.findAll(pageState);
        Optional.ofNullable(serviceResponse.get("pageState"))
                .ifPresent(p -> response.setHeader("pageState", p.toString()));
        return (Observable<HomePostResponse>) serviceResponse.get("data");
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Completable createPost(@RequestBody NewPostRequest newPostRequest) {
        return postService.createPost(newPostRequest);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Single<HomePostEntity> postDetail(@PathVariable String id) {
        return postService.detailPost(id);
    }
}
