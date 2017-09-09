package com.webpostparser.parserservice.comodity;

/**
 * Created by Domagoj Pordan on 27.08.17..
 */
public abstract class Commodity {
    public void init() {};
    public String getDate() { return null; }
    public String getCity() {
        return null;
    }
    public String getType() {
        return null;
    }
    public String getTitle() {
        return null;
    }
    public String getLink() {
        return null;
    }
    public double getArea() {
        return 0;
    }
    public double getPrice() {
        return 0;
    }
    public String getImageLink() {
        return null;
    }
}
