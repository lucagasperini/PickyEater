package com.pickyeaters.logic.view.bean;

public class AllergyBean {
    private String name;

    public AllergyBean(String name) {
        setName(name);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
