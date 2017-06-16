package com.webparser.njuskaloparser;

import com.webparser.parsehelpers.ParseHelper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

/**
 * Created by pord on 11.06.17..
 */
public class Link {
    Map<String, String> parsingConfig;

    public void setParsingConfig(Map<String, String> parsingConfig) {
        this.parsingConfig = parsingConfig;
    }

    public String getLink(Element link) {
        Element el = ParseHelper.getCointainingElement(link, parsingConfig.get("linkAttr"), parsingConfig.get("linkAttrVal"));
        if (el == null)
            return null;
        Elements els = el.getElementsByAttributeValueMatching(parsingConfig.get("linkMatchAttr"), parsingConfig.get("linkMatchAttrVal"));
        if (els.size() == 0)
            return null;
        return "www.njuskalo.hr" + els.first().attr("href");
    }
}
