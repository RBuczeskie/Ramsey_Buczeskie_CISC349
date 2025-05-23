package com.example.walmartstoreslistview;

import org.json.JSONException;
import org.json.JSONObject;

public class Store {
    protected String name;
    protected String address;
    protected String phoneNumber;
    protected String city;
    public Store(JSONObject object) throws JSONException {
            this.name = object.getString("Store_Name");
            this.address = object.getString("Address");
            this.phoneNumber = object.getString("Phone_Number");
            this.city = object.getString("city");
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
