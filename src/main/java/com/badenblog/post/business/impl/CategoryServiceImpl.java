package com.badenblog.post.business.impl;

import com.badenblog.post.business.CategoryService;
import com.badenblog.post.model.entity.CategoryEntity;
import com.badenblog.post.model.entity.HomePostEntity;
import com.badenblog.post.model.entity.PostByCategoryEntity;
import com.badenblog.post.model.json.post.CategoryIdsFilterRequest;
import com.badenblog.post.model.json.post.response.HomePostResponse;
import com.badenblog.post.model.udt.PostUdt;
import com.badenblog.post.repository.CategoryRepository;
import com.badenblog.post.repository.PostByCategoryRepository;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRespository;

    @Autowired
    PostByCategoryRepository postByCategoryRepository;

    @Override
    public Observable<CategoryEntity> findAll() {
        return categoryRespository.findAll().toObservable();
    }

    @Override
    public Observable<HomePostResponse> findByIdCategory(CategoryIdsFilterRequest categoryIdsFilterRequest) {
        Set<UUID> catIds = new HashSet<>();
        Set<UUID> postIds = new HashSet<>();
        categoryIdsFilterRequest.getCategoryIds().stream().forEach(c -> catIds.add(UUID.fromString(c)));
        return postByCategoryRepository.findAllByIdCategory(catIds)
                .flatMapIterable(this::convertPostUdtFromPostByCategoryToHomePostResponse)
                .filter(hpr->postIds.add(hpr.getId()))
                .toObservable();
    }

    private Set<HomePostResponse> convertPostUdtFromPostByCategoryToHomePostResponse(PostByCategoryEntity postByCategoryEntity) {
        Set<HomePostResponse> homePostResponses = new HashSet<>();
        Optional.ofNullable(postByCategoryEntity.getPosts()).ifPresent(posts -> {
            posts.stream().forEach(p -> {
                HomePostResponse homePostResponse = new HomePostResponse();
                homePostResponse.setId(p.getId());
                homePostResponse.setName(p.getName());
                homePostResponse.setCategories(p.getCategories());
                homePostResponse.setCreationDate(p.getCreationDate());
                homePostResponse.setDescription(p.getDescription());
                homePostResponse.setMinAge(p.getMinAge());
                homePostResponse.setMaterials(p.getMaterials());
                homePostResponses.add(homePostResponse);
            });
        });
        return homePostResponses;
    }

}
