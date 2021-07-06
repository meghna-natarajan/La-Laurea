package com.example.gradday;

import java.util.ArrayList;

public class gradUser {

    String fullName;
    String email;
    String dept;
    String rollNo;
    int rsvp; //0-> no one attending, 1->user attending, 2->on behalf of user someone else
    int seats;
    ArrayList<String> messages;

    public gradUser(){

    }

    public gradUser(String fullName, String email, String dept, String rollNo) {
        this.fullName = fullName;
        this.email = email;
        this.dept = dept;
        this.rollNo = rollNo;
        this.rsvp = 0;
        this.seats = 0;
        this.messages = new ArrayList<String>();
    }
}
