package com.petko.entities;

public class BookEntity extends Entity{
    private String title;
    private String author;

    @Override
    public String toString() {
        return String.format("Book [title=%s, author=%s]", title, author);
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
