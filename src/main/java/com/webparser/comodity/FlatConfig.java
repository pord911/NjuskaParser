package com.webparser.comodity;

/**
 * Created by pord on 12.06.17..
 */
public class FlatConfig extends Config {
    String minArea;
    String maxArea;
    String minPrice;
    String maxPrice;
    String location;
    String neighbourHood;

    public FlatConfig(String minArea,
                      String maxArea,
                      String minPrice,
                      String maxPrice,
                      String location,
                      String neighbourHood) {
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.location = location;
        this.neighbourHood = neighbourHood;
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
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getNeighbourHood() {
        return neighbourHood;
    }

    public void setNeighbourHood(String neighbourHood) {
        this.neighbourHood = neighbourHood;
    }
}
