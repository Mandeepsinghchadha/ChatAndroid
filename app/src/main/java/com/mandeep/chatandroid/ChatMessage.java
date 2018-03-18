package com.mandeep.chatandroid;

import java.io.Serializable;

/**
 * Created by Nimit Arora on 3/14/2018.
 */

public class ChatMessage implements Serializable{
    public String name;
    public  String message;
    public  String photoURL=null;

    public ChatMessage(){}
    public ChatMessage(String name, String message, String photoURL) {
        this.name = name;
        this.message = message;
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getPhotoURL() {
        return photoURL;
    }
}
