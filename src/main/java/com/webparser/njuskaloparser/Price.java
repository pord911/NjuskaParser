package com.webparser.njuskaloparser;

import com.webparser.parsehelpers.ParseHelper;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pord on 11.06.17..
 */
public class Price {
    Map<String, String> parsingConfig;

    public void setParsingConfig(Map<String, String> parsingConfig) {
        this.parsingConfig = parsingConfig;
    }

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
