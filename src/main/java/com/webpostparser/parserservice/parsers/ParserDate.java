package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.parsehelpers.ParseHelper;
import org.jsoup.nodes.Element;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Domagoj Pordan on 25.06.17..
 */
public class ParserDate {
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
    public String getDate(Element link) {
        Element el = ParseHelper.getCointainingElement(link, parsingConfig.get("dateAttr"), parsingConfig.get("dateAttrVal"));
        if (el == null)
            return null;
        if (parsingConfig.get("regex") == null)
            return el.text();

        Pattern p = Pattern.compile(parsingConfig.get("regex"));
        Matcher m = p.matcher(el.text());
        if (m.find()) {
            if (m.group(1) != null) {
                return m.group(1);
            } else {
                /* Return current day if match found but with no result. */
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.GERMAN);
                return dateFormat.format(new Date());
            }
        }
        return null;
    }
}
