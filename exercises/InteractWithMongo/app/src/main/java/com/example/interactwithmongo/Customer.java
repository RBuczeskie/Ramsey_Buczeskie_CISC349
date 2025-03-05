package com.example.interactwithmongo;

import java.util.List;

public class Customer {
    protected String name;
    protected String address;
    protected String phone;
    protected List<String> comments;

    public String getName(){
        return null == name ? "" : name;
    }
    public String getAddress(){
        return null == address ? "" : address;
    }
    public String getPhone(){
        return null == phone ? "" : phone;
    }
}
