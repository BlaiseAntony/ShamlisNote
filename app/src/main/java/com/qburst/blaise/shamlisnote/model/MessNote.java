package com.qburst.blaise.shamlisnote.model;

public class MessNote {

    private String item;
    private int id;
    private int price;
    private int year;
    private int month;
    private int day;

    public MessNote(int id, String item, int price, int year, int month, int day) {
        this.id = id;
        this.item = item;
        this.price = price;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getItem() {
        return item;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
