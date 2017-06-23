package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.parsehelpers.ParseHelper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

/**
 * Created by Domagoj Pordan on 11.06.17..
 */
public class Link {
    Map<String, String> parsingConfig;

    /**
     * Set parsing rules for parsing link from a web page.
     * @param parsingConfig   Parsing criteria for parsing link
     */
    public void setParsingConfig(Map<String, String> parsingConfig) {
        this.parsingConfig = parsingConfig;
    }

    /**
     * Return the parsed link from JSoup HTML element
     * @param link  JSoup element
     * @return      Link string
     */
    public String getLink(Element link) {
        Element el = ParseHelper.getCointainingElement(link, parsingConfig.get("linkAttr"), parsingConfig.get("linkAttrVal"));
        if (el == null)
            return null;
        Elements els = el.getElementsByAttributeValueMatching(parsingConfig.get("linkMatchAttr"), parsingConfig.get("linkMatchAttrVal"));
        if (els.size() == 0)
            return null;
        return parsingConfig.get("webpage") + els.first().attr("href");
    }
}
