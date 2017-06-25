package com.webpostparser.main;

import com.webpostparser.processor.FlatProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by pord on 11.06.17..
 */
@Configuration
public class App {
    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        FlatProcessor processor = (FlatProcessor)ctx.getBean(FlatProcessor.class);
        processor.processFlats();
    }
}
