package com.webpostparser.parserservice.parsehelpers;

import com.webpostparser.parserservice.comodity.ComodityValue;
import com.webpostparser.parserservice.parsers.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Domagoj Pordan on 11.06.17..
 */
public class ParseHelper {

    /**
     * Helper method for returning element which matches
     * html attribute and value.
     * @param el          JSoup HTML element representation
     * @param attribute   HTML attribute to match
     * @param value       HTML attribute value to match
     * @return            JSoup HTML element
     */
    public static Element getCointainingElement(Element el, String attribute, String value) {
        Elements els = el.getElementsByAttributeValueContaining(attribute, value);
        if (els.size() == 0)
            return null;
        return els.first();
    }

    /**
     * Helper function for filtering text content. Parsing rules
     * determine if we want to return success or fail on the parsed content.
     * Parsed content can be found, but if parsed rules has false for
     * boolean, means we do not want the text to be included.
     * @param el             Text
     * @param parsingRules   Map of String boolean values to parse
     * @return               True if value found and parsing rules is true
     */
    public static boolean filerContent(String el, Map<String, Boolean> parsingRules) {
        Pattern p;
        Matcher m;
        Set<String> keySet = parsingRules.keySet();
        for (String rule : keySet) {
            p = Pattern.compile(rule);
            m = p.matcher(el);
            if (m.find()) {
                if (!parsingRules.get(rule).booleanValue()) // Da li želim vratit
                    return true;
            } else if(parsingRules.get(rule).booleanValue()) { // Da li želim izbacit
                return true;
            }
        }
        return false;
    }

    /**
     * Helper function to return parsed value form the HTML.
     * @param context   Spring application context.
     * @param value     Commodity value
     * @param beanId    Id of the instantiated parser bean
     * @param element   JSoup HTML element
     * @return          Parsed value as an Object
     */
    public static Object getParsedValue(ApplicationContext context, ComodityValue value, String beanId, Element element) {
        switch (value) {
            case AREA:
                Area area = (Area)context.getBean(beanId + "Area");
                return area.getArea(element);
            case LINK:
                Link link = (Link)context.getBean(beanId + "Link");
                return link.getLink(element);
            case PRICE:
                Price price = (Price)context.getBean(beanId + "Price");
                return price.getPrice(element);
            case TITLE:
                Title title = (Title)context.getBean(beanId + "Title");
                return title.getTitle(element);
            case IMAGE:
                Image image = (Image)context.getBean(beanId + "Image");
                return image.getImageLink(element);
            default:
                return null;

        }
    }

    /**
     * Calculate link hash string.
     * @param link                          Link
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String calculateHash(String link)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] linkBytes = link.getBytes("UTF-8");
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] mdVal = digest.digest(linkBytes);

        return DatatypeConverter.printHexBinary(mdVal);
    }
}
