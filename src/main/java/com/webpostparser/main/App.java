package com.webpostparser.main;

import com.webpostparser.parserservice.comodity.FlatConfig;
import com.webpostparser.parserservice.parsers.FlatParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pord on 11.06.17..
 */
@Configuration
public class App {
    
    public static void main(String[] args) throws IOException {
        Map<String, Boolean> parsingRules = new HashMap<String, Boolean>();
        //parsingRules.put("Suteren|suteren|SUTEREN", false);
        parsingRules.put("HITNO|hitno", true);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        FlatParser parser = (FlatParser) ctx.getBean(FlatParser.class);
        FlatConfig config = new FlatConfig("0", "0", "0", "0", "zagreb", "");
        try {
            parser.getPosts(parsingRules, config);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
