package com.example.personalapplication.model;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Wallet extends LitePalSupport {

    private String title;
    private String amount;
    private Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
