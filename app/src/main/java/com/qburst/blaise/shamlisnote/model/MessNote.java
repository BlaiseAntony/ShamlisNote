package com.qburst.blaise.shamlisnote.model;

public class MessNote {

    private String date;
    private String item;
    private int id;
    private int price;

    public MessNote(int id, String date, String item, int price) {
        this.id = id;
        this.date = date;
        this.item = item;
        this.price = price;
    }

    public String getDate() {
        return date;
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
}
