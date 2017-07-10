package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.comodity.ComodityType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by Domagoj Pordan on 08.07.17..
 */
@Component
public class ParserFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    public Parser getParser(ComodityType type) {
        switch (type) {
            case HOUSE:
            case FLAT:
                return context.getBean(FlatParser.class);
        }
        return null;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
