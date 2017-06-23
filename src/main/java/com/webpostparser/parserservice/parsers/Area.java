package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.parsehelpers.ParseHelper;
import org.jsoup.nodes.Element;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Domagoj Pordan on 11.06.17..
 */
public class Area {
    Map<String, String> parsingConfig;

    /**
     * Set parsing rules for parsing area from a web page.
     * @param parsingConfig   Parsing criteria for parsing area
     */
    public void setParsingConfig(Map<String, String> parsingConfig) {
        this.parsingConfig = parsingConfig;
    }

    /**
     * Return the parsed area from JSoup HTML element
     * @param link  JSoup HTML element
     * @return      Area number
     */
    public Double getArea(Element link) {
        Element el = ParseHelper.getCointainingElement(link, parsingConfig.get("areaAttr"), parsingConfig.get("areaAttrVal"));
        if (el == null)
            return null;
        Pattern p = Pattern.compile(parsingConfig.get("regex"));
        Matcher m = p.matcher(el.text());
        if (!m.find())
            return null;
        return Double.valueOf(m.group(1));
    }
}
