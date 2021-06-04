package com.rateLimiter.ratelimitersolution.model;

public class Developer {

    private int id;
    private String name;
    private String department;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Developer(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }
}
