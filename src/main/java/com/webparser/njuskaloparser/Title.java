package com.webparser.njuskaloparser;

import com.webparser.parsehelpers.ParseHelper;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Map;

/**
 * Created by pord on 11.06.17..
 */
public class Title {
    Map<String, String> parsingConfig;

    public void setParsingConfig(Map<String, String> parsingConfig) {
        this.parsingConfig = parsingConfig;
    }

    public String getTitle(Element link, List<String> parsingRules) {
        Element el = ParseHelper.getCointainingElement(link, parsingConfig.get("elAttribute"), parsingConfig.get("attrValue"));
        if (el == null)
            return null;
        /* parsingRules will have two meanings,
         * One: if the filtered value is in the string return that string
         * Two: if the filtered value is in the string, skip it */
        if (parsingRules != null && ParseHelper.filerContent(el, parsingRules))
            return null;
        return el.text();
    }
}