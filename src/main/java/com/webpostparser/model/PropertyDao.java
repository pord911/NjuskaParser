package com.webpostparser.model;

import com.webpostparser.parserservice.comodity.Commodity;
import com.webpostparser.parserservice.comodity.CommodityFactory;
import com.webpostparser.parserservice.comodity.Property;
import com.webpostparser.parserservice.parsehelpers.DateFormatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Property Data Access Object
 *
 * @author  Domagoj Pordan on 22.06.17..
 */
@Repository
public class PropertyDao implements DaoCommoditynterface {

    @Autowired
    private CommodityFactory commodityFactory;

    private JdbcTemplate jdbcTemplate;
    private final String PROPERTY_TABLE = "TestFlats";

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
    public List<Commodity> getList() {
        final List<Commodity> propertyList = new LinkedList<Commodity>();

        jdbcTemplate.query("SELECT * FROM \"" + PROPERTY_TABLE + "\"", new RowMapper<Property>() {
            public Property mapRow(ResultSet resultSet, int i) throws SQLException {
                Property flat = commodityFactory.createProperty(resultSet.getString("postlink"));

                flat.setArea(resultSet.getDouble("flatarea"));
                flat.setImageLink(resultSet.getString("imagelink"));
                flat.setPrice(resultSet.getDouble("price"));
                flat.setTitle(resultSet.getString("title"));
                flat.setCity(resultSet.getString("city"));
                flat.setFlatType(resultSet.getString("flat_type"));
                flat.setDate(DateFormatHelper.getDatePostFormat(
                        resultSet.getString("pubdate")));
                propertyList.add(flat);
                return flat;
            }
        });
        return propertyList;
    }

    /**
     * Insert a list of flat posts in the database.
     * @param flatList  List of flat posts.
     * @return          Bool indicating successfull operation
     */
    public <T extends Commodity> boolean insertList(List<T> flatList) {
        int[][] i = jdbcTemplate.batchUpdate("INSERT INTO \"" + PROPERTY_TABLE + "\" " +
                        "(city, title, postlink, imagelink, price, flatarea, pubdate, flat_type)  " +
                        "VALUES (?, ?, ?, ?, ?, ?, to_date(?, 'yyyy-mm-dd'), ?)", flatList, flatList.size(),
                new ParameterizedPreparedStatementSetter<T>() {
                    public void setValues(PreparedStatement preparedStatement, T property) throws SQLException {

                        preparedStatement.setString(1, property.getCity());
                        preparedStatement.setString(2, property.getTitle());
                        preparedStatement.setString(3, property.getLink());
                        preparedStatement.setString(4, property.getImageLink());
                        preparedStatement.setDouble(5, property.getPrice());
                        preparedStatement.setDouble(6, property.getArea());
                        preparedStatement.setString(7,
                                DateFormatHelper.getDateDatabaseFormat(property.getDate()));
                        preparedStatement.setString(8, property.getType());
                    }
                });
        return i.length > 0;
    }

    public <T extends Commodity> boolean update(List<T> list) {
        int[][] i = jdbcTemplate.batchUpdate("UPDATE \"" + PROPERTY_TABLE + "\" SET city=?, title=?, imagelink=?, price=?" +
                        "flatarea=?, pubdate=to_date(?, 'yyyy-mm-dd'), flat_rype=? WHERE postlink=? ", list, list.size(),
                new ParameterizedPreparedStatementSetter<T>() {
                    public void setValues(PreparedStatement preparedStatement, T property) throws SQLException {

                        preparedStatement.setString(1, property.getCity());
                        preparedStatement.setString(2, property.getTitle());
                        preparedStatement.setString(3, property.getImageLink());
                        preparedStatement.setDouble(4, property.getPrice());
                        preparedStatement.setDouble(5, property.getArea());
                        preparedStatement.setString(6,
                                DateFormatHelper.getDateDatabaseFormat(property.getDate()));
                        preparedStatement.setString(7, property.getType());
                        preparedStatement.setString(8, property.getLink());
                    }
                });
        return i.length > 0;
    }

    public <T extends Commodity> boolean delete(List<T> list) {
        int[][] i = jdbcTemplate.batchUpdate("DELETE FROM \"" + PROPERTY_TABLE + "\" WHERE postlink=? ", list, list.size(),
                new ParameterizedPreparedStatementSetter<T>() {
                    public void setValues(PreparedStatement preparedStatement, T property) throws SQLException {
                        preparedStatement.setString(1, property.getLink());
                    }
                });
        return i.length > 0;
    }

    /**
     * Return hash hash values of links belonging to
     * a particular post.
     * @return  HasSet of link hash values
     */
    public Map<Property, Property> getHashValues() {
        final Map<Property, Property> linkHashSet = new HashMap<Property, Property>();

        jdbcTemplate.query("SELECT postlink, price, flatarea FROM \"" + PROPERTY_TABLE + "\"", new RowMapper<String>() {
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                Property property = commodityFactory.createProperty(resultSet.getString("postlink"));
                // TODO: Change flatarea to area in database
                property.setArea(resultSet.getDouble("flatarea"));
                property.setPrice(resultSet.getDouble("price"));
                linkHashSet.put(property, property);
                return null;
            }
        });
        System.out.println(linkHashSet.size());
        return linkHashSet;
    }
}
