package com.rent.afor.space.tolet.kaizer.tolet.model_data;

/**
 * Created by kaizer on 3/12/17.
 */

public class CommentContent {
    private String userPic, userName, dateAndTime, comment;

    public CommentContent(String userPic, String userName, String dateAndTime, String comment) {

        this.userPic = userPic;
        this.userName = userName;
        this.dateAndTime = dateAndTime;
        this.comment = comment;

    }

    public String getUserPic() {
        return userPic;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getComment() {
        return comment;
    }

}
