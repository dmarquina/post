package com.badenblog.post.model.entity;

import com.badenblog.post.model.udt.PostDisplayUdt;
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
@Table("schedule")
public class ScheduleEntity {
    @PrimaryKeyColumn(
            name = "user_id",
            ordinal = 1,
            type = PrimaryKeyType.PARTITIONED)
    private String userId;

    @PrimaryKeyColumn(
            name = "application_date",
            ordinal = 2,
            type = PrimaryKeyType.CLUSTERED)
    private UUID applicationDate;

    @Column("posts")
    private Set<PostDisplayUdt> posts;

    @Column("name")
    private String name;

}
