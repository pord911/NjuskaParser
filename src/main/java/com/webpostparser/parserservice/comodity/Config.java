package com.webpostparser.parserservice.comodity;

/**
 * Created by Domagoj Pordan on 15.06.17..
 */

/**
 * Abstract class for setting request configuration
 */
public abstract class Config {
    public String getMinArea() { return null; }

    public String getMaxArea() { return null; }

    public String getMinPrice() { return null; }

    public String getMaxPrice() { return null; }

    public String getCity() { return null; }

    public String getNeighbourHood() { return null; }
}
