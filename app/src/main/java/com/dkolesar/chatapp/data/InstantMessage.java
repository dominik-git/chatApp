package com.dkolesar.chatapp.data;

public class InstantMessage {
    private String message;
    private String author;

    public InstantMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        InstantMessage message = (InstantMessage) obj;

        return message.message.equals(this.message) && message.author.equals(this.author);
    }
}
