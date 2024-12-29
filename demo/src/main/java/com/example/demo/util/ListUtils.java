package com.example.demo.util;

import java.util.NoSuchElementException;
import java.util.List;

public class ListUtils {


    public static<T> T getLast(List<T> list) throws RuntimeException{
        if(list.isEmpty()){
            throw new NoSuchElementException("List is Empty");
        }

        return list.get(list.size()-1);
    }

    public static<T> T getFirst(List<T> list) throws RuntimeException{
        if(list.isEmpty()){
            throw new NoSuchElementException("List is Empty");
        }

        return list.get(0);
    }


}
