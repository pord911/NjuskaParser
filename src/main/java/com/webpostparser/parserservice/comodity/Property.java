package com.webpostparser.parserservice.comodity;

import com.webpostparser.parserservice.parsehelpers.LinkHashHelper;

/**
 * Created by Domagoj Pordan on 08.07.17..
 */

public class Property extends Commodity {
    LinkHashHelper linkHashHelper;

    String title;
    String link;
    String imageLink;
    String city;
    int linkHash = 0;
    String flatType;
    String date;
    double area;
    double price;

    public Property(String link, LinkHashHelper linkHashHelper) {
        this.link = link;
        this.linkHashHelper = linkHashHelper;
    }

    public void init() {
        this.linkHash = linkHashHelper.getHashValueOfString(link);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return flatType;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public void setCity(String city) {
        this.city = city;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property flat = (Property) o;

        if (linkHash != flat.linkHash) return false;
        if (Double.compare(flat.area, area) != 0) return false;
        return Double.compare(flat.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return linkHash;
    }
}
