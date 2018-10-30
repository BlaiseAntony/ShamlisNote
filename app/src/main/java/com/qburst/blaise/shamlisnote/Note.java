package com.qburst.blaise.shamlisnote;

public class Note {
    public String getBody() {
        return Body;
    }

    public String getHead() {
        return Head;
    }

    private String Head;
    private String Body;
    private int id;

    Note(int id, String Head, String Body) {
        this.id=id;
        this.Head = Head;
        this.Body = Body;
    }

    public int getId() {
        return id;
    }
}
