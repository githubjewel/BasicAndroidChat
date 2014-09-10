package com.kushal.chatton.Beans;

public class Chat {

    private String message;
    private String sender;
    private String sent_time;
    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Chat() { }

    public Chat(String message, String sender, String sent_time) {
        this.message = message;
        this.sender = sender;
        this.sent_time = sent_time;
    }

	public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

	public String getSent_time() {
		return sent_time;
	}
}
