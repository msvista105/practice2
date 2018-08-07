package com.example.sxm.eventbus;

public class MessageEvent {
    private String message;

    public MessageEvent(String m) {
        this.message = m;
    }

    public void setMessage(String m) {
        this.message = m;
    }

    public String getMessage() {
        return message;
    }
}
