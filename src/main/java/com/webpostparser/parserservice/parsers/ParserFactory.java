package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.comodity.ComodityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Domagoj Pordan on 08.07.17..
 */
@Configuration
public class ParserFactory {

    @Autowired
    PropertyParser propertyParser;

    public Parser createParser(ComodityType type) {
        switch (type) {
            case HOUSE:
            case FLAT:
                return propertyParser;
        }
        return null;
    }
}
