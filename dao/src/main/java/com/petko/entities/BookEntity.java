package com.petko.entities;

public class BookEntity extends Entity{
    private int bookId;
    private String title;
    private String author;

    @Override
    public String toString() {
        return String.format("Book [bookId=%d, title=%s, author=%s]", bookId, title, author);
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
