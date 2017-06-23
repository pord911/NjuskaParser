package com.webpostparser.model;

import com.webpostparser.parserservice.posts.Flat;
import com.webpostparser.parserservice.posts.FlatList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by pord on 22.06.17..
 */
public class FlatsDao {
   /* private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbc(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public FlatList getFlat() {
        return jdbcTemplate.query("select * from Flats", RowMapper<Flat>);
    } */
}
