package com.webpostparser.parserservice.parsers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Domagoj Pordan on 17.06.17..
 */
public class Image {
    Map<String, String> parsingConfig;

    /**
     * Set parsing rules for parsing image from a web page.
     * @param parsingConfig   Parsing criteria for parsing image
     */
    public void setParsingConfig(Map<String, String> parsingConfig) {
        this.parsingConfig = parsingConfig;
    }

    /**
     * Return the parsed image from JSoup HTML element
     * @param element  JSoup HTML element
     * @return         Image link string
     */
    public String getImageLink(Element element) {
        Elements els = element.getElementsByAttributeValueMatching(parsingConfig.get("imageAttr"), parsingConfig.get("imageAttrVal"));
        if (els.size() == 0)
            return null;
        Element el = els.first();
        Pattern p = Pattern.compile(parsingConfig.get("regex"));
        Matcher m = p.matcher(el.attr(parsingConfig.get("imageAttr")));
        if (!m.find())
            return null;
        return m.group();
    }
}
