package com.webparser.njuskaloparser;

import com.webparser.parsehelpers.ParseHelper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pord on 11.06.17..
 */
public class Area {
    Map<String, String> parsingConfig;

    public void setParsingConfig(Map<String, String> parsingConfig) {
        this.parsingConfig = parsingConfig;
    }

    public Double getArea(Element link) {
        Element el = ParseHelper.getCointainingElement(link, parsingConfig.get("areaAttr"), parsingConfig.get("areaAttrVal"));
        if (el == null)
            return null;
        Pattern p = Pattern.compile("Stambena površina: (\\d+\\.\\d+)");
        Matcher m = p.matcher(el.text());
        if (!m.find())
            return null;
        return Double.valueOf(m.group(1));
    }
}
