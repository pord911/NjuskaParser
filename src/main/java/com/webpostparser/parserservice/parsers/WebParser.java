package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.comodity.ComodityType;
import com.webpostparser.parserservice.comodity.ComodityValue;
import com.webpostparser.parserservice.comodity.Config;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Domagoj Pordan on 15.06.17..
 */
public interface WebParser {
    public String constructUrl(ComodityType comodityType, Config config);
    public String constructUrl(ComodityType comodityType, Config config, int pageNum);
    public int getPageNumber(String url) throws IOException;
    public Elements getElements(String url) throws IOException;
    public Object getValueForComodity(ComodityType type, ComodityValue value, Element element);
    public boolean isWebPage(String webPage);
}
