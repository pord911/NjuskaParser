package com.webparser.parsehelpers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pord on 11.06.17..
 */
public class ParseHelper {
    public static Element getCointainingElement(Element el, String attribute, String value) {
        //System.out.println("Get element with attribue: " + attribute + " and value: " + value);
        Elements els = el.getElementsByAttributeValue(attribute, value);
        //System.out.println("Elements size: " + els.size());
        if (els.size() == 0)
            return null;
        return els.first();
    }

    public static boolean filerContent(Element el, List<String> parsingRules) {
        Pattern p;
        Matcher m;
        for (String rule : parsingRules) {
            p = Pattern.compile(rule);
            m = p.matcher(el.text());
            if (m.find())
                return true;
        }
        return false;
    }
}
