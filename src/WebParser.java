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
 * Created by pord911 on 4.6.2017..
 */
public class WebParser {

    public static void main(String[] args) throws IOException {  // Probati viditi da li postoji format funkcija koja znakove kao što su %d zamijeni sa brojevima
        String pageNumberLink = "http://www.njuskalo.hr/prodaja-stanova/zagreb?price%5Bmin%5D=10000&price%5Bmax%5D=50000&mainArea%5Bmin%5D=40&mainArea%5Bmax%5D=70&page=";
        int num = FlatParser.getPageNumber("http://www.njuskalo.hr/prodaja-stanova/zagreb&price[min]=10000&price[max]=50000&mainArea[min]=40&mainArea[max]=70");
        List<FlatPost> posts = new LinkedList<>();
        for(int i = 0; i < num; i++) {
            List<FlatPost> list = FlatParser.processPosts(pageNumberLink + (i+1));
            posts.addAll(list);
        }

        for( FlatPost post : posts ) {
            if (post.getArea() > post.getPrice()) {
                System.out.println("Title: " + post.getTitle());
                System.out.println("Link: " + post.getLink());
                System.out.println("Area: " + post.getArea());
                System.out.println("Price: " + post.getPrice());
            }
        }
    }
}
