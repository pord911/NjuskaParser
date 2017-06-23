package com.webpostparser.parserservice.posts;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Domagoj Pordan on 5.6.2017..
 */
public class Flat {
    String title;
    String link;
    String imageLink;
    double area;
    double price;
    String linkHash;

    public String getLinkHash() {
        return linkHash;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set link string and calculate and set link hash string.
     * @param link                          Link
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public void setLinkAndCalculateHash(String link) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.link = link;
        byte[] linkBytes = link.getBytes("UTF-8");
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] mdVal = digest.digest(linkBytes);
        linkHash = DatatypeConverter.printHexBinary(mdVal);
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public double getArea() {
        return area;
    }

    public double getPrice() {
        return price;
    }
}
