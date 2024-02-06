package com.pickyeaters.logic.model;

public class Allergy {
    private String ID;
    private String name;

    public Allergy(String id, String name) {
        setID(id);
        setName(name);
    }
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
