package com.webpostparser.model;

import com.webpostparser.parserservice.parsehelpers.DateFormatHelper;
import com.webpostparser.parserservice.posts.CommodityList;
import com.webpostparser.parserservice.posts.CommodityPost;
import com.webpostparser.parserservice.posts.Flat;
import com.webpostparser.parserservice.posts.FlatList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Domagoj Pordan on 22.06.17..
 */
@Repository
public class FlatsDao implements DaoFactoryInterface {
    private JdbcTemplate jdbcTemplate;
    private String tableName = "Flats";

    /**
     * Set JDBC data source.
     * @param dataSource  Configured data source
     */
    @Autowired
    public void setJdbc(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    /**
     * Return list of all flat posts from the database.
     * @return
     */
    public CommodityList getList() {
        final FlatList flatList = new FlatList();

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
                flat.setFlatType(resultSet.getString("flat_type"));
                flat.setDate(DateFormatHelper.getDatePostFormat(
                        resultSet.getString("pubdate")));
                flatList.add(flat);
                return flat;
            }
        });
        return flatList;
    }

    /**
     * Insert a list of flat posts in the database.
     * @param flatList  List of flat posts.
     * @return          Bool indicating successfull operation
     */
    public boolean insertList(Collection<CommodityPost> flatList) {
        int[][] i = jdbcTemplate.batchUpdate("INSERT INTO \"" + tableName + "\" " +
                        "(city, title, postlink, imagelink, price, flatarea, linkhash, pubdate, flat_type)  " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, to_date(?, 'yyyy-mm-dd'), ?)", flatList, flatList.size(),
                new ParameterizedPreparedStatementSetter<CommodityPost>() {
                    public void setValues(PreparedStatement preparedStatement, CommodityPost commodityPost) throws SQLException {
                        Flat flat = (Flat)commodityPost.getObject();

                        preparedStatement.setString(1, flat.getCity());
                        preparedStatement.setString(2, flat.getTitle());
                        preparedStatement.setString(3, flat.getLink());
                        preparedStatement.setString(4, flat.getImageLink());
                        preparedStatement.setDouble(5, flat.getPrice());
                        preparedStatement.setDouble(6, flat.getArea());
                        preparedStatement.setString(7, flat.getLinkHash());
                        preparedStatement.setString(8,
                                DateFormatHelper.getDateDatabaseFormat(flat.getDate()));
                        preparedStatement.setString(9, flat.getFlatType());
                    }
                });
        return i.length > 0;
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
