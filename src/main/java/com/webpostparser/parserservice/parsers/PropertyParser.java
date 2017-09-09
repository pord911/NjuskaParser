package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.comodity.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Domagoj Pordan on 11.06.17.
 */
@Component
public class PropertyParser implements Parser {
    @Autowired
    List<WebParser> webParsers;
    @Autowired
    private CommodityFactory propertyFactory;

    private final int MAX_NUMBER_OF_PAGES = 1000;
    private Property lastFlatElement;
    private ComodityType comodityType;
    private String city;

    public ComodityType getComodityType() {
        return comodityType;
    }

    public void setComodityType(ComodityType comodityType) {
        this.comodityType = comodityType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    /**
     * Get flat ads for all web pages which are defined in the system.
     * Look into beans.xml for all defined web pages and parsers.
     * @return              List of flat posts.
     * @throws IOException
     */
    public void processPosts(CommodityProcessor commodityProcessor, ComodityType comodityType, String city)
            throws IOException {
        Elements elements;
        boolean processedElements = false;

        setCity(city);
        setComodityType(comodityType);
        for (WebParser webParser : webParsers) {
            /* A finite loop is added since we do not want to
             * query web pages infinetely if something goes wrong */
            for (int i = 1; i < MAX_NUMBER_OF_PAGES; i++) {
                elements = webParser.getElements(webParser.constructUrl(comodityType, city, i));

                /* TODO: Add a log here */
                if (elements.size() == 0)
                    break;
                processedElements = processFlatElements(webParser, elements, commodityProcessor);

                if (processedElements)
                    break;
            }
        }
    }

    /**
     * Process flat posts returned from the web page.
     * @param webParser      Parser holding parsing methods for a specific web site.
     * @param elements       JSoup elements of a particular web page.
     * @return               boolean which indicates if we reached the end of web page posts.
     */
    private boolean processFlatElements(WebParser webParser, Elements elements, CommodityProcessor commodityProcessor) {
        Property flat = null;
        List<Property> propertyList = new LinkedList<Property>();
        String titleVal, linkVal, image, date;
        Double priceVal, areaVal;

        for (Element element : elements) {
            linkVal = (String) webParser.getValueForComodity(comodityType, ComodityValue.LINK, element);
            if (linkVal == null)
                continue;

            /* Njuskalo has unwanted posts which do not fit our criteria, so we also consider
             * area as a filer factor for njuskalo. */
            areaVal = (Double) webParser.getValueForComodity(comodityType, ComodityValue.AREA, element);
            if (areaVal == null && webParser.isWebPage("njuskalo"))
                continue;

            titleVal = (String) webParser.getValueForComodity(comodityType, ComodityValue.TITLE, element);
            priceVal = (Double) webParser.getValueForComodity(comodityType, ComodityValue.PRICE, element);
            image = (String) webParser.getValueForComodity(comodityType, ComodityValue.IMAGE, element);
            date = (String) webParser.getValueForComodity(comodityType, ComodityValue.DATE, element);

            flat = propertyFactory.createProperty(linkVal);
            if (priceVal == null)
                flat.setPrice(0);
            else
                flat.setPrice(priceVal);

            if (areaVal == null)
                flat.setArea(0);
            else
                flat.setArea(areaVal);


            flat.setTitle(titleVal);
            flat.setImageLink(image);
            flat.setCity(city);
            flat.setFlatType(comodityType.name());
            flat.setDate(date);

            propertyList.add(flat);
        }

        if (flat == null)
            return true;
        commodityProcessor.processCommodities(propertyList);
        /* We compare the hash of the last element to see if we reached the last
         * flat post web page. */
        if (!flat.equals(lastFlatElement)) {
            lastFlatElement = flat;
            return false;
        }
        return true;
    }
}