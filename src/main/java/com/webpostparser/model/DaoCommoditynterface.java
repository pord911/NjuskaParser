package com.webpostparser.model;

import com.webpostparser.parserservice.comodity.Commodity;

import java.util.List;
import java.util.Map;

/**
 * Created by pord on 08.07.17..
 */
public interface DaoCommoditynterface {
    public  List<Commodity> getList();
    public <T extends Commodity> boolean insertList(List<T> flatList);
    public <T extends Commodity> boolean update(List<T> flatList);
    public <T extends Commodity> boolean delete(List<T> flatList);
    public <T extends Commodity> Map<T, T> getHashValues();

}
