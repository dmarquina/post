package com.badenblog.post.model.entity;

import com.badenblog.post.model.udt.PostUdt;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Table("post_by_category")
public class PostByCategoryEntity {
    @PrimaryKeyColumn(
            name = "id_category",
            ordinal = 1,
            type = PrimaryKeyType.PARTITIONED)
    private UUID idCategory;

    @Column("posts")
    private Set<PostUdt> posts;

}
