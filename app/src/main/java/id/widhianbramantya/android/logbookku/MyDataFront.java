package id.widhianbramantya.android.logbookku;

/**
 * Created by kang ady on 09/04/2017.
 */

public class MyDataFront {
    String id, title, date, type, room, activity, next_activity;

    public MyDataFront(String id, String title, String date, String type, String room, String activity, String next_activity) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.type = type;
        this.room = room;
        this.activity = activity;
        this.next_activity = next_activity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getNext_activity() {
        return next_activity;
    }

    public void setNext_activity(String next_activity) {
        this.next_activity = next_activity;
    }

    public String getId() {
        return id;
    }

}
