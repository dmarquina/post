package com.badenblog.post.business.impl;

import com.badenblog.post.business.PostService;
import com.badenblog.post.model.entity.HomePostEntity;
import com.badenblog.post.model.entity.PostByCategoryEntity;
import com.badenblog.post.model.json.post.NewPostRequest;
import com.badenblog.post.model.udt.PostUdt;
import com.badenblog.post.repository.PostByCategoryRepository;
import com.badenblog.post.repository.PostRepository;
import com.badenblog.post.util.Pagination;
import com.datastax.driver.core.*;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service("postService")
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepository postRespository;

    @Autowired
    PostByCategoryRepository postByCategoryRepository;

    @Autowired
    Cluster cluster;

    @Override
    public Map findAll(String requestPageState){
        String query = "select * from home_post";
        Pagination pagination = new Pagination();
        ResultSet resultSet = pagination.cassandraPagination(cluster,requestPageState,query);
        Iterator<Row> iterator = resultSet.iterator();

        List<HomePostEntity> postFeed= new ArrayList<>();
        while (resultSet.getAvailableWithoutFetching() > 0 && iterator.hasNext()) {
            postFeed.add(convertRowPostToPostEntity(iterator.next()));
        }
        PagingState responsePageState = resultSet.getExecutionInfo().getPagingState();

        Map response = new HashMap();
        Optional.ofNullable(responsePageState).ifPresent( rps -> response.put("pageState", rps.toString()));
        response.put("data",Observable.fromIterable(postFeed));

        return response;
    }

    @Override
    public Completable createPost(NewPostRequest newPostRequest) {
        HomePostEntity homePostEntity = convertNewPostRequestToEntity(newPostRequest);
        savePostByCategory(newPostRequest, homePostEntity);
        return postRespository.save(homePostEntity).toCompletable();
    }

    @Override
    public Single<HomePostEntity> detailPost(String id){
        return postRespository.findByIdPost(UUID.fromString(id));
    }


    private void savePostByCategory(NewPostRequest newPostRequest, HomePostEntity homePostEntity) {
        Optional.ofNullable(newPostRequest.getCategories()).ifPresent(categories -> {
            categories.stream().forEach(cat -> {
                postByCategoryRepository.findByIdCategory(UUID.fromString(cat.getId()))
                        .defaultIfEmpty(new PostByCategoryEntity())
                        .map(pbc -> {
                            if (pbc.getIdCategory() == null) {
                                pbc.setIdCategory(UUID.fromString(cat.getId()));
                            }
                            Set<PostUdt> ps = pbc.getPosts() != null ? pbc.getPosts() : new HashSet<>();
                            ps.add(convertHomeEntityToPostUdt(homePostEntity));
                            pbc.setPosts(ps);
                            postByCategoryRepository.save(pbc).subscribe();
                            return pbc;
                        }).subscribe();
            });
        });
    }

    private PostUdt convertHomeEntityToPostUdt(HomePostEntity homePostEntity){
        PostUdt postUdt = new PostUdt();
        postUdt.setId(homePostEntity.getId());
        postUdt.setName(homePostEntity.getName());
        postUdt.setCategories(homePostEntity.getCategories());
        postUdt.setCreationDate(homePostEntity.getCreationDate());
        postUdt.setDescription(homePostEntity.getDescription());
        postUdt.setMinAge(homePostEntity.getMinAge());
        postUdt.setMaterials(homePostEntity.getMaterials());
        return postUdt;
    }

    private HomePostEntity convertNewPostRequestToEntity(NewPostRequest newPostRequest) {
        HomePostEntity homePostEntity = new HomePostEntity();
        homePostEntity.setId(UUID.randomUUID());
        homePostEntity.setName(newPostRequest.getName());
        homePostEntity.setDescription(newPostRequest.getDescription());
        homePostEntity.setMinAge(newPostRequest.getMinAge());
        Set materials = new HashSet(newPostRequest.getMaterials());
        homePostEntity.setMaterials(materials);
        homePostEntity.setCreationDate(LocalDate.fromMillisSinceEpoch(new Date().getTime()));
        Optional.ofNullable(newPostRequest.getCategories())
                .ifPresent(cats ->
                homePostEntity.setCategories(
                        new HashSet(cats.stream()
                        .map(c -> c.getName())
                        .collect(Collectors.toSet()))));

        return homePostEntity;
    }

    private HomePostEntity convertRowPostToPostEntity(Row row){
        HomePostEntity homePostEntity = new HomePostEntity();

        homePostEntity.setId((row.getUUID("id")));
        homePostEntity.setName(row.getString("name"));
        homePostEntity.setDescription(row.getString("description"));
        homePostEntity.setMinAge(row.getInt("min_age"));
        homePostEntity.setCreationDate(row.getDate("creation_date"));
        homePostEntity.setCategories(row.getSet("categories",String.class));
        homePostEntity.setMaterials(row.getSet("materials",String.class));

        return homePostEntity;
    }

}