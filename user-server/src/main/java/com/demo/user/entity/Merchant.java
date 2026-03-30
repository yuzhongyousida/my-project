package com.demo.user.entity;

public class Merchant {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String description;

    public Merchant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public