package com.webpostparser.model;

import com.webpostparser.parserservice.posts.Flat;
import com.webpostparser.parserservice.posts.FlatList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Domagoj Pordan on 22.06.17..
 */
@Component
public class FlatsDao {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private String tableName = "Flats";

    /**
     * Set JDBC data source.
     * @param dataSource  Configured data source
     */
    @Autowired
    public void setJdbc(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }

    /**
     * Return list of all flat posts from the database.
     * @return
     */
    public FlatList getFlats() {
        FlatList flatList = new FlatList();

        List<Flat> list = jdbcTemplate.query("select * from \"" + tableName + "\"", new RowMapper<Flat>() {
            public Flat mapRow(ResultSet resultSet, int i) throws SQLException {
                Flat flat = new Flat();
                flat.setArea(resultSet.getDouble("flatarea"));
                flat.setImageLink(resultSet.getString("imagelink"));
                flat.setLink(resultSet.getString("postlink"));
                flat.setPrice(resultSet.getDouble("price"));
                flat.setTitle(resultSet.getString("title"));
                flat.setLinkHash(resultSet.getString("linkhash"));
                flat.setCity(resultSet.getString("city"));
                return flat;
            }
        });
        for (Flat flat : list) {
            flatList.add(flat);
        }
        return flatList;
    }

    /**
     * Insert a list of flat posts in the database.
     * @param flatList  List of flat posts.
     * @return          Bool indicating successfull operation
     */
    public boolean insertFlats(List<Flat> flatList) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(flatList.toArray());
        return jdbcTemplate.batchUpdate("INSERT INTO \"" + tableName + "\" " +
                "(city, title, postlink, imagelink, price, flatarea, linkhash)  " +
                "VALUES (:city, :title, :link, :imageLink, :price, :area, :linkHash)", batch).length == flatList.size();
    }

    /**
     * Return hash hash values of links belonging to
     * a particular post.
     * @return  HasSet of link hash values
     */
    public HashSet<String> getHashValues() {
        HashSet<String> linkHashSet = new HashSet<String>();
        List<String> list = jdbcTemplate.query("select linkhash from \"" + tableName + "\"", new RowMapper<String>() {
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("linkhash");
            }
        });
        System.out.println(list.size());
        for (String hash : list) {
            if (!linkHashSet.add(hash))
                System.out.println("Element already exists: " + hash);
        }
        return linkHashSet;
    }
}
