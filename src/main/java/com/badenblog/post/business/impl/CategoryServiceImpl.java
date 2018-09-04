package com.badenblog.post.business.impl;

import com.badenblog.post.business.CategoryService;
import com.badenblog.post.model.entity.CategoryEntity;
import com.badenblog.post.model.json.CategoryIdsFilterRequest;
import com.badenblog.post.model.json.response.HomePostResponse;
import com.badenblog.post.repository.CategoryRepository;
import com.badenblog.post.repository.PostByCategoryRepository;
import com.badenblog.post.util.Pagination;
import com.datastax.driver.core.*;
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

    @Autowired
    Cluster cluster;

    @Override
    public Observable<CategoryEntity> findAll() {
        return categoryRespository.findAll().toObservable();
    }

    @Override
    public Map findByIdCategory(CategoryIdsFilterRequest categoryIdsFilterRequest, String requestPageState) {
        String catIds = "";
        for (int i = 0; i < categoryIdsFilterRequest.getCategoryIds().size(); i++) {
            catIds += (i == categoryIdsFilterRequest.getCategoryIds().size() - 1) ?
                    categoryIdsFilterRequest.getCategoryIds().get(i) :
                    categoryIdsFilterRequest.getCategoryIds().get(i) + ",";
        }
        String query = "select * from post_by_category where id_category in (" + catIds + ")";
        Pagination pagination = new Pagination();
        ResultSet resultSet = pagination.cassandraPagination(cluster, requestPageState, query);
        Iterator<Row> iterator = resultSet.iterator();

        Set<HomePostResponse> homePostResponse = new HashSet<>();
        while (resultSet.getAvailableWithoutFetching() > 0 && iterator.hasNext()) {
            homePostResponse.addAll(convertRowPostToHomePostResponse(iterator.next()));
        }

        Set<UUID> postIds = new HashSet<>();
        homePostResponse = homePostResponse.stream()
                .filter(hpr -> postIds.add(hpr.getId()))
                .collect(Collectors.toSet());

        PagingState responsePageState = resultSet.getExecutionInfo().getPagingState();

        Map response = new HashMap();
        Optional.ofNullable(responsePageState)
                .ifPresent(rps -> response.put("pageState", rps.toString()));
        response.put("data", Observable.fromIterable(homePostResponse));

        return response;

    }

    private Set<HomePostResponse> convertRowPostToHomePostResponse(Row row) {
        Set<UDTValue> posts = row.getSet("posts", UDTValue.class);
        if (posts != null) {
            return posts.stream()
                    .map(p -> {
                        HomePostResponse homePostResponse = new HomePostResponse();
                        homePostResponse.setId((p.getUUID("id")));
                        homePostResponse.setName(p.getString("name"));
                        homePostResponse.setDescription(p.getString("description"));
                        homePostResponse.setMinAge(p.getInt("min_age"));
                        homePostResponse.setCreationDate(p.getDate("creation_date"));
                        homePostResponse.setCategories(p.getSet("categories", String.class));
                        homePostResponse.setMaterials(p.getSet("materials", String.class));
                        homePostResponse.setUid((p.getString("uid")));
                        homePostResponse.setUserDisplayName(p.getString("user_displayname"));
                        return homePostResponse;
                    })
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

}
