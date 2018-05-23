package com.badenblog.post.model.entity;

import com.datastax.driver.core.LocalDate;
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
@Table("home_post")
public class HomePostEntity {
    @PrimaryKeyColumn(
            name = "id",
            ordinal = 1,
            type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("min_age")
    private int minAge ;

    @Column("creation_date")
    private LocalDate creationDate;

    @Column("categories")
    private Set<String> categories = new HashSet<>();

    @Column("materials")
    private Set<String> materials= new HashSet<>();
}
