package com.petko.vo;

import com.petko.entities.PlaceOfIssue;

import java.util.Date;

public class OrderForMyOrdersList {
    private int orderId;
    private int bookId;
    private String title;
    private String author;
    private PlaceOfIssue place;
    private Date startDate;

    public OrderForMyOrdersList(int orderId, int bookId, /*String title, String author,*/ PlaceOfIssue place, Date startDate) {
        this.orderId = orderId;
        this.bookId = bookId;
//        this.title = title;
//        this.author = author;
        this.place = place;
        this.startDate = startDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public PlaceOfIssue getPlace() {
        return place;
    }

    public Date getStartDate() {
        return startDate;
    }
}
