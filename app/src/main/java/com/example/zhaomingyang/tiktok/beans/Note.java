package com.example.zhaomingyang.tiktok.beans;
import com.example.zhaomingyang.tiktok.beans.State;

import java.util.Date;
public class Note {



    public final long id;

    private Date date;

    private State state;

    private String content;



    public Note(long id) {

        this.id = id;

    }



    public Date getDate() {

        return date;

    }



    public void setDate(Date date) {

        this.date = date;

    }



    public State getState() {

        return state;

    }



    public void setState(State state) {

        this.state = state;

    }



    public String getContent() {

        return content;

    }



    public void setContent(String content) {

        this.content = content;

    }

}
