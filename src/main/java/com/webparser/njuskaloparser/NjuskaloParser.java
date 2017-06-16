package com.webparser.njuskaloparser;

import com.webparser.comodity.Config;
import com.webparser.comodity.ComodityType;
import com.webparser.comodity.ComodityValue;
import com.webparser.parsers.WebParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.List;

/**
 * Created by pord911 on 5.6.2017..
 */
@Component
public class NjuskaloParser implements WebParser, ApplicationContextAware {
    ApplicationContext context;

    public Object getValueForComodity(ComodityType type, ComodityValue value, Element element) {
        return getValueForComodity(type, value, element, null);
    }

    public Object getValueForComodity(ComodityType type, ComodityValue value, Element element, List<String> parsingRules) {
        switch (type) {
            case FLAT:
                return getParsedValue(value, "njuskaloFlat", element, parsingRules);
            default:
                return null;
        }
    }

    private Object getParsedValue(ComodityValue value, String beanId, Element element, List<String> parsingRules) {
        switch (value) {
            case AREA:
                Area area = (Area)context.getBean(beanId + "Area");
                return area.getArea(element);
            case LINK:
                Link link = (Link)context.getBean(beanId + "Link");
                return link.getLink(element);
            case PRICE:
                Price price = (Price)context.getBean(beanId + "Price");
                return price.getPrice(element);
            case TITLE:
                Title title = (Title)context.getBean(beanId + "Title");
                return title.getTitle(element, parsingRules);
            default:
                return null;

        }
    }

    public String constructUrl(ComodityType type, Config config) {
        return constructUrl(type, config, 1);
    }

    public String constructUrl(ComodityType type, Config config, int pageNum) {
        switch(type) {
            case FLAT:
                return constructFlatUrl(config, pageNum);
            default:
                return null;
        }
    }

    private String constructFlatUrl(Config config, int pageNum) {
        StringBuilder buildUrl = new StringBuilder("http://www.njuskalo.hr/");
        buildUrl.append("prodaja-stanova").append("/").append(config.getLocation());
        buildUrl.append("?price%5Bmin%5D=").append(config.getMinPrice());
        buildUrl.append("&price%5Bmax%5D=").append(config.getMaxPrice());
        buildUrl.append("&mainArea%5Bmin%5D=").append(config.getMinArea());
        buildUrl.append("&mainArea%5Bmax%5D=").append(config.getMaxArea());
        if (pageNum != 0)
            buildUrl.append("&page=").append(pageNum);
        System.out.println("Built njuskalo url: " + buildUrl.toString());
        return buildUrl.toString();
    }

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

    public Elements getElements(String url) throws IOException {
        Document doc;
        doc = Jsoup.connect(url).get();
        return doc.getElementsByTag("article");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}