package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.comodity.ComodityType;
import com.webpostparser.parserservice.comodity.ComodityValue;
import com.webpostparser.parserservice.comodity.Config;
import com.webpostparser.parserservice.posts.FlatList;
import com.webpostparser.parserservice.posts.Flat;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * Created by Domagoj Pordan on 11.06.17..
 */
@Configuration
public class FlatParser {
    @Autowired
    List<WebParser> pageParserList;
    final int MAX_NUMBER_OF_PAGES = 1000;
    String lastElementHash = "0";

    /**
     * Get flat posts for all web pages which are defined in the system.
     * Look into beans.xml for all defined web pages and parsers.
     * @param parsingRules  Deprecated
     * @param config        Configuration which is used to form the web page URL
     * @return              List of flat posts
     * @throws IOException
     */
    public FlatList getPosts(Map<String, Boolean> parsingRules, Config config) throws IOException, UnsupportedEncodingException, NoSuchAlgorithmException {
        Elements elements;
        FlatList flatList = new FlatList();
        boolean processedElements = false;

        for (WebParser pageParser : pageParserList) {
            /* A finite loop is added since we do not want to
             * query web pages infinetly if something goes wrong */
            for (int i = 1; i < MAX_NUMBER_OF_PAGES; i++) {
                elements = pageParser.getElements(pageParser.constructUrl(ComodityType.FLAT, config, i));

                if (elements.size() == 0)
                    break;
                processedElements = processFlatElements(elements, parsingRules, flatList, pageParser);

                if (processedElements)
                    break;
            }
        }
        return flatList;
    }

    /**
     * Process flat posts returned from the web page.
     * @param elements       JSoup elements of a particular web page
     * @param parsingRules   Deprecated
     * @param flatList       List of flat elements
     * @param pageParser     Specific web page parser
     * @return               boolean which indicates of parsing was succesful or not
     */
    private boolean processFlatElements(Elements elements, Map<String, Boolean> parsingRules, FlatList flatList, WebParser pageParser) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Flat flat;
        String titleVal, linkVal, image;
        Double priceVal, areaVal;
        FlatList tmpFlatList = new FlatList();

        for (Element element : elements) {
            linkVal = (String)pageParser.getValueForComodity(ComodityType.FLAT, ComodityValue.LINK, element);
            if (linkVal == null)
                continue;

            /* Njuskalo has unwanted posts which do not fit our criteria, so we also consider
             * area as a filer factor for njuskalo. */
            areaVal = (Double)pageParser.getValueForComodity(ComodityType.FLAT, ComodityValue.AREA, element);
            if (areaVal == null && pageParser.isWebPage("njuskalo"))
                continue;

            titleVal = (String) pageParser.getValueForComodity(ComodityType.FLAT, ComodityValue.TITLE, element);
            priceVal = (Double) pageParser.getValueForComodity(ComodityType.FLAT, ComodityValue.PRICE, element);
            image = (String) pageParser.getValueForComodity(ComodityType.FLAT, ComodityValue.IMAGE, element);

            flat = new Flat();

            if (priceVal == null)
                flat.setPrice(0);
            else
                flat.setPrice(priceVal);

            if (areaVal == null)
                flat.setArea(0);
            else
                flat.setArea(areaVal);

            flat.setLinkAndCalculateHash(linkVal);
            flat.setTitle(titleVal);
            flat.setImageLink(image);

            tmpFlatList.add(flat);
        }

        if (tmpFlatList.isListEmpty())
            return true;

        /* We compare the hash of the last element to see if we reached the last
         * flat post web page. */
        if (!lastElementHash.equals(tmpFlatList.getLastElement().getLinkHash())) {
            lastElementHash = tmpFlatList.getLastElement().getLinkHash();
            for (Flat post : tmpFlatList.getList()) {
                //if (!ParseHelper.filerContent(post.getTitle(), parsingRules)) // This should be moved out, but leave the concept
                                                                                // as it will be done when parsing tables
                    flatList.add(post);
            }
            return false;
        }
        return true;
    }
}
