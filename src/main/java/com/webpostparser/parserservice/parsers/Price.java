package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.parsehelpers.ParseHelper;
import org.jsoup.nodes.Element;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Domagoj Pordan on 11.06.17..
 */
public class Price {
    Map<String, String> parsingConfig;

    /**
     * Set parsing rules for parsing price from a web page.
     * @param parsingConfig   Parsing criteria for parsing price
     */
    public void setParsingConfig(Map<String, String> parsingConfig) {
        this.parsingConfig = parsingConfig;
    }

    /**
     * Return the parsed price from JSoup HTML element
     * @param link  JSoup HTML element
     * @return      Price number
     */
    public Double getPrice(Element link) {
        Element el = ParseHelper.getCointainingElement(link, parsingConfig.get("priceAttr"), parsingConfig.get("priceAttrVal"));
        if (el == null)
            return null;
        Pattern p = Pattern.compile("(\\d+\\.\\d+)");
        Matcher m = p.matcher(el.text());
        if (!m.find())
            return null;
        return Double.valueOf(m.group());
    }
}
