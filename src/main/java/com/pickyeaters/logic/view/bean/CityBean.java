package com.pickyeaters.logic.view.bean;

public class CityBean {
    private String name;

    public CityBean(String name) {
        setName(name);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
