package com.example.ylmz.recyclevieweventfinder;

public class Event {

    private String title;
    private String description;
    private String imageURL;
    private String time;

    public Event(String title, String time, String imageURL,String description) {
        this.title = title;
        this.description = description;
        this.imageURL=imageURL;
        this.time=time;
    }



    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;

    }
    public  String getTime(){
        return time;
    }
}
