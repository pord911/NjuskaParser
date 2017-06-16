package com.webparser.parsers;

import com.webparser.comodity.ComodityType;
import com.webparser.comodity.ComodityValue;
import com.webparser.comodity.Config;
import com.webparser.posts.FlatList;
import com.webparser.posts.FlatPost;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Created by pord on 11.06.17..
 */
@Configuration
public class FlatParser {
    Config config;
    @Autowired
    List<WebParser> pageParserList;

    public void initParser(Config config) {
        this.config = config;
    }
    /* TODO: parsingRules can be a Map<Rule:STRING, isWanted:BOOLEAN>
 *       which states if the user wants or doesn't want a
 *       particular item from the post */
    public void getPosts(List<String> parsingRules) throws IOException {
        Elements elements;
        FlatList flatList = new FlatList();
        int numOfPages;
        /* It's ok for numOfPages to be 0 at some situations,
         * since page number will default to 1 in the loop and call
         * constructurl with a page=1 which will return the
         * url for a single page. */
        for (WebParser pageParser : pageParserList) {
            numOfPages = pageParser.getPageNumber(pageParser.constructUrl(ComodityType.FLAT, config));
            for (int i = 1; i < numOfPages + 1; i++) {
                elements = pageParser.getElements(pageParser.constructUrl(ComodityType.FLAT, config, i));
                processFlatElements(elements, parsingRules, flatList, pageParser);
            }
        }
        if (flatList.isListEmpty())
            System.out.println("List is empty");
        for (FlatPost post : flatList.getList()) {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println(post.getTitle());
            System.out.println(post.getLink());
            System.out.println(post.getArea());
            System.out.println(post.getPrice());
            System.out.println("--------------------------------------------------------------------------------------");
        }
    }

    private void processFlatElements(Elements elements, List<String> parsingRules, FlatList flatList, WebParser pageParser) {
        FlatPost flatPost;
        String titleVal, linkVal;
        Double priceVal, areaVal;
        for (Element element : elements) {
            if ( (linkVal = (String)pageParser.getValueForComodity(ComodityType.FLAT, ComodityValue.LINK, element)) == null )
                continue;
            if ((areaVal = (Double) pageParser.getValueForComodity(ComodityType.FLAT, ComodityValue.AREA, element)) == null)
                continue;
            titleVal = (String) pageParser.getValueForComodity(ComodityType.FLAT, ComodityValue.TITLE, element, parsingRules);
            priceVal = (Double) pageParser.getValueForComodity(ComodityType.FLAT, ComodityValue.PRICE, element);

            flatPost = new FlatPost();
            flatPost.setPrice(priceVal.doubleValue());
            flatPost.setLink(linkVal);
            flatPost.setArea(areaVal.doubleValue());
            flatPost.setTitle(titleVal);

            flatList.add(flatPost);
        }
    }
}
