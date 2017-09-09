package com.webpostparser.model;

import com.webpostparser.parserservice.comodity.ComodityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by pord on 08.07.17..
 */
@Configuration
public class DaoFactory {

    @Autowired
    PropertyDao propertyDao;

    public DaoCommoditynterface createDao(ComodityType type) {
        switch (type) {
            case FLAT:
            case HOUSE:
                return propertyDao;
        }
        return null;
    }
}
