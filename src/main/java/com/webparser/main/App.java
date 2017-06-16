package com.webparser.main;

import com.webparser.comodity.FlatConfig;
import com.webparser.parsers.FlatParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pord on 11.06.17..
 */
@Configuration
public class App {
    
    public static void main(String[] args) throws IOException {
        List<String> parsingRules = new LinkedList<String>();
        parsingRules.add("Suteren|suteren|SUTEREN");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        FlatParser parser = (FlatParser) ctx.getBean(FlatParser.class);
        FlatConfig config = new FlatConfig("40", "70", "35000", "60000", "zagreb", "");
        parser.initParser(config);
        parser.getPosts(parsingRules);
    }
}
