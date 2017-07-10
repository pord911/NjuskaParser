package com.webpostparser.parserservice.posts;

/**
 * Created by Domagoj Pordan on 08.07.17..
 */
public class Flat implements CommodityPost {

    String title;
    String link;
    String imageLink;
    String city;
    String linkHash;
    String flatType;
    String date;
    double area;
    double price;

    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public String getFlatType() {
        return flatType;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
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

    public Object getObject() {
        return this;
    }
}
