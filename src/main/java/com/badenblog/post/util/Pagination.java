package com.badenblog.post.util;

import com.datastax.driver.core.*;

import java.util.Optional;

public class Pagination {

    private static final String KEYSPACE = "badenblog";
    private static final int FETCH_SIZE = 10;




    public ResultSet cassandraPagination(Cluster cluster, String requestPageState,String query){
        Session session = cluster.connect(KEYSPACE);
        Statement statement = new SimpleStatement(query);
        statement.setFetchSize(FETCH_SIZE);

        Optional.ofNullable(requestPageState).ifPresent( p -> {
            try {
                PagingState pagingState = PagingState.fromString(p);
                statement.setPagingState(pagingState);
            }catch (RuntimeException re){
                throw new RuntimeException("No se especifico la paginación correctamente");
            }
            }
        );

        ResultSet resultSet = session.execute(statement);
        session.close();

        return resultSet;
    }
}
