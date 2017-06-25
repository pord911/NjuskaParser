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
    String city;
    String linkHash;
    double area;
    double price;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLinkHash() {
        return linkHash;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setLinkHash(String linkHash) {
        this.linkHash = linkHash;
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
