package com.rent.afor.space.tolet.kaizer.tolet.model_data;

/**
 * Created by kaizer on 3/6/17.
 */

public class FeedContent {

    private String userPic;
    private String userName;
    private String dateAndTime;
    private String priceOfFlat;
    private String sizeOfFlat;
    private String noOfBed;
    private String noOfBath;
    private String floorNo;
    private String addressOfFlat;
    private String otherInfo;
    private String postId;
    private String flatAvailableTime;

    public FeedContent(String userPic, String userName, String dateAndTime, String priceOfFlat, String sizeOfFlat, String noOfBed, String noOfBath, String floorNo, String addressOfFlat, String otherInfo, String postId, String flatAvailableTime) {

        this.userPic = userPic;
        this.userName = userName;
        this.dateAndTime = dateAndTime;
        this.priceOfFlat = priceOfFlat;
        this.sizeOfFlat = sizeOfFlat;
        this.noOfBed = noOfBed;
        this.noOfBath = noOfBath;
        this.floorNo = floorNo;
        this.addressOfFlat = addressOfFlat;
        this.otherInfo = otherInfo;
        this.postId = postId;
        this.flatAvailableTime = flatAvailableTime;


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

    public String getPriceOfFlat() {
        return priceOfFlat;
    }

    public String getSizeOfFlat() {
        return sizeOfFlat;
    }

    public String getNoOfBed() {
        return noOfBed;
    }

    public String getNoOfBath() {
        return noOfBath;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public String getAddressOfFlat() {
        return addressOfFlat;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public String getPostId() {
        return postId;
    }

    public String getFlatAvailableTime() {
        return flatAvailableTime;
    }

}
