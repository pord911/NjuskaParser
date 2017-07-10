package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.comodity.ComodityType;
import com.webpostparser.parserservice.comodity.ComodityValue;
import com.webpostparser.parserservice.parsehelpers.ParseHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * Created by Domagoj Pordan on 5.6.2017..
 */
@Component
public class NjuskaloParser implements WebParser, ApplicationContextAware {
    ApplicationContext context;
    final String WEB_PAGE = "njuskalo";

    /**
     * Set spring application context
     * @param applicationContext  Spring application context
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     * Return an object representing the parsed value for
     * a particular comodity.
     * @param comodityType    Type of comodity
     * @param comodityValue   Value to be parsed, i.e. LINK|AREA etc...
     * @param element         JSoup element
     * @return                Parsed value as an Object
     */
    public Object getValueForComodity(ComodityType comodityType, ComodityValue comodityValue, Element element) {
        switch (comodityType) {
            case FLAT:
                return ParseHelper.getParsedValue(context, comodityValue, "njuskaloFlat", element);
            default:
                return null;
        }
    }

    /**
     * Match a web page.
     * @param webPage  Web page name.
     * @return         True or False
     */
    public boolean isWebPage(String webPage) {
        return WEB_PAGE.equals(webPage);
    }


    /**
     * Construct a www.njuskalo.hr url
     * @param type     Type of commodity
     * @param city      Which city for pulling web pages
     * @param pageNum  Particular web page number
     * @return         URL
     */
    public String constructUrl(ComodityType type, String city, int pageNum) {
        switch(type) {
            case FLAT:
                return constructFlatUrl("prodaja-stanova", city, pageNum);
            default:
                return null;
        }
    }

    /**
     * Construct a www.njuskalo.hr URL for returning flat posts
     * @param city      Which city for pulling web pages
     * @param pageNum   Particular web page number
     * @return          URL
     */
    private String constructFlatUrl(String linkText, String city, int pageNum) {
        StringBuilder buildUrl = new StringBuilder("http://www.njuskalo.hr/");
        buildUrl.append(linkText).append("/").append(city);
        buildUrl.append("&page=").append(pageNum);
        System.out.println("Built njuskalo url: " + buildUrl.toString());
        return buildUrl.toString();
    }

    // Deprecated
    public int getPageNumber(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.getElementsByAttributeValueContaining("class","Pagination-items");
        if (links.size() == 0)
            return 0;
        Elements elems = links.first().getElementsByAttributeValueContaining("class", "Pagination-item");
        if (elems.size() == 0)
            return 0;
        return elems.size() - 2;
    }

    /**
     * Return all post elements for a particular URL
     * @param url          Web page URL
     * @return             JSoup Elements
     * @throws IOException
     */
    public Elements getElements(String url) throws IOException {
        Document doc;
        doc = Jsoup.connect(url).get();
        return doc.getElementsByTag("article");
    }
}