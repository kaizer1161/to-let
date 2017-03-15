package com.rent.afor.space.tolet.kaizer.tolet.model_data;

/**
 * Created by kaizer on 3/6/17.
 */

public class FeedContent {

    private String userPic, userName, dateAndTime, priceOfFlat, sizeOfFlat, noOfBed, noOfBath, floorNo, addressOfFlat, otherInfo, postId;

    public FeedContent(String userPic, String userName, String dateAndTime, String priceOfFlat, String sizeOfFlat, String noOfBed, String noOfBath, String floorNo, String addressOfFlat, String otherInfo, String postId) {

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
}
