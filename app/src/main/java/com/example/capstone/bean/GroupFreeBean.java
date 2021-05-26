package com.example.capstone.bean;

public class GroupFreeBean {
    private String name;
    private GroupFreeBeanElement[] elements;
    public GroupFreeBean(String name, GroupFreeBeanElement[] elements) {
        this.name = name;
        this.elements = elements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setElements(GroupFreeBeanElement[] elements) {
        this.elements = elements;
    }

    public GroupFreeBeanElement[] getElements() {
        return elements;
    }
}
