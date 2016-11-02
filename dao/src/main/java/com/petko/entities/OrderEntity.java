package com.petko.entities;

import java.util.Date;

public class OrderEntity extends Entity{
    private int orderId;
    private String login;
    private int bookId;
    private OrderStatus status;
    private PlaceOfIssue placeOfIssue;
    private Date startDate;
    private Date endDate;

    @Override
    public String toString() {
        return String.format("Order [orderId=%d, login=%s, bookId=%d, status=%s, startDate=%s, endDate=%s]",
                orderId, login, bookId, status, startDate, endDate);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public PlaceOfIssue getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(PlaceOfIssue placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
