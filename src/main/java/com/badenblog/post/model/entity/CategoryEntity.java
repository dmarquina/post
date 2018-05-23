package com.badenblog.post.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Table("category")
public class CategoryEntity {
    @PrimaryKeyColumn(
            name = "id",
            ordinal = 1,
            type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("state")
    private String state;

}
