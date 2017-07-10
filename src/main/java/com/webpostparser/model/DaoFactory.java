package com.webpostparser.model;

import com.webpostparser.parserservice.comodity.ComodityType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by pord on 08.07.17..
 */
@Component
public class DaoFactory implements ApplicationContextAware {
    private static ApplicationContext context;

    public DaoFactoryInterface createDao(ComodityType type) {
        switch (type) {
            case FLAT:
            case HOUSE:
                return context.getBean(FlatsDao.class);
        }
        return null;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
