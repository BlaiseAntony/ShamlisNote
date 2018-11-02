package com.qburst.blaise.shamlisnote.model;

public class MyNote {
    public String getBody() {
        return Body;
    }

    public String getHead() {
        return Head;
    }

    private String Head;
    private String Body;
    private int id;

    public MyNote(int id, String Head, String Body) {
        this.id=id;
        this.Head = Head;
        this.Body = Body;
    }

    public int getId() {
        return id;
    }
}
