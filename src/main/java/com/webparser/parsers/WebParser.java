package com.webparser.parsers;

import com.webparser.comodity.ComodityType;
import com.webparser.comodity.ComodityValue;
import com.webparser.comodity.Config;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by pord on 15.06.17..
 */
public interface WebParser {
    public String constructUrl(ComodityType comodityType, Config config);
    public String constructUrl(ComodityType comodityType, Config config, int pageNum);
    public int getPageNumber(String url) throws IOException;
    public Elements getElements(String url) throws IOException;
    public Object getValueForComodity(ComodityType type, ComodityValue value, Element element);
    public Object getValueForComodity(ComodityType type, ComodityValue value, Element element, List<String> parsingRules);
}
