package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.comodity.ComodityType;
import com.webpostparser.parserservice.comodity.ComodityValue;
import com.webpostparser.parserservice.parsehelpers.ParseHelper;
import com.webpostparser.parserservice.posts.CommodityList;
import com.webpostparser.parserservice.posts.CommodityPost;
import com.webpostparser.parserservice.posts.FlatList;
import com.webpostparser.parserservice.posts.Flat;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Domagoj Pordan on 11.06.17..
 */
@Component
public class FlatParser implements Parser {
    @Autowired
    List<WebParser> pageParserList;
    private final int MAX_NUMBER_OF_PAGES = 1000;
    private final String INIT_HASH = "0";
    String lastElementHash = INIT_HASH;
    FlatList tmpFlatList = new FlatList();

    /**
     * Get flat ads for all web pages which are defined in the system.
     * Look into beans.xml for all defined web pages and parsers.
     * @param city          Location
     * @param type          Type of commodity
     * @param linkHashSet   Hash list of already stored ads
     * @return              List of flat posts
     * @throws IOException
     */
    public CommodityList getPosts(String city, ComodityType type, HashSet<String> linkHashSet)
            throws IOException, NoSuchAlgorithmException {
        Elements elements;
        FlatList flatList = new FlatList();
        boolean processedElements = false;

        for (WebParser pageParser : pageParserList) {
            /* A finite loop is added since we do not want to
             * query web pages infinetely if something goes wrong */
            for (int i = 1; i < MAX_NUMBER_OF_PAGES; i++) {
                elements = pageParser.getElements(pageParser.constructUrl(type, city, i));

                /* TODO: Add a log here */
                if (elements.size() == 0)
                    break;
                processedElements = processFlatElements(elements, city, flatList, pageParser, linkHashSet, type);

                if (processedElements)
                    break;
            }
        }
        return flatList;
    }

    /**
     * Process flat posts returned from the web page.
     * @param elements       JSoup elements of a particular web page
     * @param flatList       List of flat elements
     * @param pageParser     Specific web page parser
     * @param linkHashSet    Set of link hashes
     * @return               boolean which indicates of parsing was succesful or not
     */
    private boolean processFlatElements(Elements elements, String city, FlatList flatList,
                                        WebParser pageParser, HashSet<String> linkHashSet, ComodityType type)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Flat flat;
        String titleVal, linkVal, image, linkHash = INIT_HASH, date;
        Double priceVal, areaVal;

        for (Element element : elements) {
            linkVal = (String) pageParser.getValueForComodity(type, ComodityValue.LINK, element);
            if (linkVal == null)
                continue;

            /* Njuskalo has unwanted posts which do not fit our criteria, so we also consider
             * area as a filer factor for njuskalo. */
            areaVal = (Double) pageParser.getValueForComodity(type, ComodityValue.AREA, element);
            if (areaVal == null && pageParser.isWebPage("njuskalo"))
                continue;

            linkHash = ParseHelper.calculateHash(linkVal);
            /* If link hash set is not empty, then we need to see
             * if we already have this element stored. */
            if (linkHashSet != null && !linkHashSet.isEmpty()) {
                if (linkHashSet.contains(linkHash)) {
                    linkHashSet.remove(linkHash);
                    continue;
                }
            }
            titleVal = (String) pageParser.getValueForComodity(type, ComodityValue.TITLE, element);
            priceVal = (Double) pageParser.getValueForComodity(type, ComodityValue.PRICE, element);
            image = (String) pageParser.getValueForComodity(type, ComodityValue.IMAGE, element);
            date = (String) pageParser.getValueForComodity(type, ComodityValue.DATE, element);

            flat = new Flat();
            if (priceVal == null)
                flat.setPrice(0);
            else
                flat.setPrice(priceVal.doubleValue());

            if (areaVal == null)
                flat.setArea(0);
            else
                flat.setArea(areaVal.doubleValue());

            flat.setLink(linkVal);
            flat.setLinkHash(linkHash);
            flat.setTitle(titleVal);
            flat.setImageLink(image);
            flat.setCity(city);
            flat.setFlatType(type.name());
            flat.setDate(date);

            tmpFlatList.add(flat);
        }

        /* We compare the hash of the last element to see if we reached the last
         * flat post web page. */
        if (!lastElementHash.equals(linkHash)) {
            lastElementHash = linkHash;
            for (CommodityPost post : tmpFlatList.getList()) {
                    flatList.add(post);
            }
            tmpFlatList.getList().clear();
            return false;
        }
        tmpFlatList.getList().clear();
        return true;
    }
}
