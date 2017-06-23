package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.parsehelpers.ParseHelper;
import org.jsoup.nodes.Element;

import java.util.Map;

/**
 * Created by Domagoj Pordan on 11.06.17..
 */
public class Title {
    Map<String, String> parsingConfig;

    /**
     * Set parsing rules for parsing title from a web page.
     * @param parsingConfig   Parsing criteria for parsing title
     */
    public void setParsingConfig(Map<String, String> parsingConfig) {
        this.parsingConfig = parsingConfig;
    }

    /**
     * Return the parsed title from JSoup HTML element
     * @param link  JSoup HTML element
     * @return      Title string
     */
    public String getTitle(Element link) {
        Element el = ParseHelper.getCointainingElement(link, parsingConfig.get("elAttribute"), parsingConfig.get("attrValue"));
        if (el == null)
            return null;
        return el.text();
    }
}