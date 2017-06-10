import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pord911 on 5.6.2017..
 */
public class FlatParser {

    public static List<FlatPost> processPosts(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.getElementsByTag("article");
        FlatPost post;
        String title, postLink;
        double price, area;
        List<FlatPost> postList = new LinkedList<>();
        for (Element link : links) {
            if ( ((title = getTitle(link)) != null) &&
                 ((area = getArea(link)) != 0) &&
                 ((price = getPrice(link)) != 0) &&
                 ((postLink = getLink(link)) != null) ) {
                post = new FlatPost();
                post.setArea(area);
                post.setLink(postLink);
                post.setPrice(price);
                post.setTitle(title);
                postList.add(post);
            }
        }
        if (postList.isEmpty())
            System.out.println("No information available on the provided site: " + url);

        return postList;
    }
    // Da li je moguće u spring-u ovdje staviti anotaciju sa parametrima za parsiranje
    public static int getPageNumber(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.getElementsByAttributeValueContaining("class","Pagination-items");
        if (links.size() == 0)
            return 0;
        Elements elems = links.first().getElementsByAttributeValueContaining("class", "Pagination-item");
        if (elems.size() == 0)
            return 0;
        return elems.size() - 2;
    }

    private static double getArea(Element link) {
        Elements els = link.getElementsByAttributeValue("class", "entity-description-main");
        Pattern p = Pattern.compile("Stambena površina: (\\d+\\.\\d+)");
        if (els.size() == 0)
            return 0;
        Element e = els.first();
        Matcher m = p.matcher(e.text());
        if (m.find())
            return Double.parseDouble(m.group(1));
        else
            return 0; //Maybe take a log here
    }

    private static String getLink(Element link) {
        Element el = getContainingElement(link, "class", "entity-title");
        if (el == null)
            return null;
        Elements els = el.getElementsByAttributeValueMatching("href", "nekretnine");
        if (els.size() == 0)
            return null;
        return "www.njuskalo.hr" + els.first().attr("href");
    }

    private static double getPrice(Element link) {
        Element el = getContainingElement(link, "class", "price price--eur");
        if (el == null)
            return 0;
        Pattern p = Pattern.compile("(\\d+\\.\\d+)");
        Matcher m = p.matcher(el.text());
        if (!m.find())
            return 0;
        return Double.parseDouble(m.group());
    }

    private static String getTitle(Element link) {
        Element el = getContainingElement(link, "class", "entity-title");
        if (el == null)
            return null;
        Pattern p = Pattern.compile("suteren|SUTEREN");
        Matcher m = p.matcher(el.text());
        if (m.find())
            return null;
        return el.text();
    }

    private static Element getContainingElement(Element link, String attribute, String value) {
        Elements els = link.getElementsByAttributeValue(attribute, value);
        if (els.size() == 0)
            return null;
        return els.first();
    }

    private String filterTitle() {
        return null;
    }
}
