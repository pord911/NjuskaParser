package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.comodity.ComodityType;
import com.webpostparser.parserservice.comodity.ComodityValue;
import com.webpostparser.parserservice.comodity.Config;
import com.webpostparser.parserservice.parsehelpers.ParseHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Domagoj Pordan on 16.06.17..
 */
public class IndexParser implements WebParser, ApplicationContextAware {
    ApplicationContext applicationContext;
    Map<String, String> indexComodityIdMap;
    Map<String, String> indexLocationIdMap;
    final String WEB_PAGE="index";

    /**
     * Set map of commodity type and id. www.index.hr
     * uses indexes in urls for commodities.
     * @param indexComodityIdMap  Commodity index map
     */
    public void setIndexComodityIdMap(Map<String, String> indexComodityIdMap) {
        this.indexComodityIdMap = indexComodityIdMap;
    }

    /**
     * Set map of location type and id. www.index.hr
     * uses indexes in urls for locations.
     * @param indexLocationIdMap  Location index map
     */
    public void setIndexLocationIdMap(Map<String, String> indexLocationIdMap) {
        this.indexLocationIdMap = indexLocationIdMap;
    }

    /**
     * Set spring application context
     * @param applicationContext  Application context
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Construct a www.njuskalo.hr url
     * @param comodityType     Type of commodity
     * @param config   Url parameters
     * @return         URL
     */
    public String constructUrl(ComodityType comodityType, Config config) {
        return constructUrl(comodityType, config, 1);
    }

    /**
     * Construct a www.njuskalo.hr URL for returning flat posts
     * @param config    URL parameters
     * @param pageNum   Particular web page number
     * @return          URL
     */
    public String constructUrl(ComodityType comodityType, Config config, int pageNum) {
        StringBuilder buildUrl = new StringBuilder("http://www.index.hr/oglasi/");

        /* TODO: Check for location area and city location, they should be separate
         * in Config, check njuskalo */
        buildUrl.append(getComodityId(comodityType));
        buildUrl.append("?pojamZup=").append(getLocationId(config.getCity()));
        buildUrl.append("&tipoglasa=1&sortby=1&elementsNum=10&grad=0&naselje=0&cijenaod=0");
        buildUrl.append("&cijenado=6000000");
        buildUrl.append("&vezani_na=988-887_562-563_978-1334&num=").append(pageNum);

        System.out.println("Built index.hr url: " + buildUrl.toString());
        return buildUrl.toString();
    }

    //Deprecated
    public int getPageNumber(String url) throws IOException {
        return 0;
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
        return doc.getElementsByAttributeValue("class", "OglasiRezHolder");
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
                return ParseHelper.getParsedValue(applicationContext, comodityValue, "indexFlat", element);
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
     * Get id number of a particular commodity.
     * @param comodityType   Type of commodity
     * @return               Id
     */
    private String getComodityId(ComodityType comodityType) {
        switch (comodityType) {
            case FLAT:
                return indexComodityIdMap.get("flat");
            default:
                return null;
        }
    }

    /**
     * Get id of a particular location.
     * @param location   Location
     * @return           Id
     */
    private String getLocationId(String location) {
        return indexLocationIdMap.get(location);
    }
}
