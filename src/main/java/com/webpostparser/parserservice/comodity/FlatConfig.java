package com.webpostparser.parserservice.comodity;

/**
 * Created by pord on 12.06.17..
 */
public class FlatConfig extends Config {
    String minArea;
    String maxArea;
    String minPrice;
    String maxPrice;
    String city;

    public FlatConfig(String city) {
        this.city = city;
    }

    public FlatConfig(String minArea,
                      String maxArea,
                      String minPrice,
                      String maxPrice,
                      String city) {
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.city = city;
    }

    @Override
    public String getMinArea() {
        return minArea;
    }

    public void setMinArea(String minArea) {
        this.minArea = minArea;
    }

    @Override
    public String getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(String maxArea) {
        this.maxArea = maxArea;
    }

    @Override
    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
