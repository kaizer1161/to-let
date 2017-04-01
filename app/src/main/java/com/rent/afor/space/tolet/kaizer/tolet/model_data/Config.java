package com.rent.afor.space.tolet.kaizer.tolet.model_data;

/**
 * ALl the key values are saved in this class,
 * sharedpreference values, database col name,
 * database responce array name, backend php link.
 */

public class Config {

    /*
    *SharedPreferences header values
    */
    public static final String SP_EMAIL = "email";
    public static final String SP_USERNAME = "username";
    public static final String SP_LOGED_IN = "login";
    public static final String SP_TOLET_APP = "sp_tolet_app";
    public static final String SP_USER_IMAGE = "sp_user_iamge";

    /*
    *database col names, to fetch and push values using backend.
    */
    public static final String JSON_ARRAY = "job_post";
    public static final String COMMENT_ARRAY = "comments";
    public static final String PROFILE_TIMELINE_ARRAY = "user_timeline_post";
    public static final String USER_LOGIN_INFO = "user_login_info";
    public static final String COMMENT_CONTENT = "comment";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_NEW_PASSWORD = "new_password";
    public static final String KEY_USER_PHONE = "phone";
    public static final String KEY_USERNAME = "name";
    public static final String KEY_DEVICE_ID = "device_id";
    public static final String KEY_USER_IMAGE = "profile_pic";
    public static final String KEY_PRICE = "price";
    public static final String KEY_SIZE_OF_FLAT = "size_of_flat";
    public static final String KEY_NO_OF_BED = "no_of_bed";
    public static final String KEY_NO_OF_BATH = "no_of_bath";
    public static final String KEY_FLOOR = "floor";
    public static final String KEY_LOCATION = "address_of_flat";
    public static final String KEY_OTHER_INFORMATION = "content";
    public static final String KEY_RENT_DATE = "date_to_rent";
    public static final String STATUS_TIME = "created_at";
    public static final String KEY_POST_ID = "id";
    public static final String KEY_COMMENT_POST_ID = "post_id";

    /*
    *  All php wed links
    */
    public static final String SIGNUP_URL = "http://tolet.artefactplus.com/signup.php";
    public static final String UPLOAD_USER_PIC_URL = "http://tolet.artefactplus.com/upload.php";
    public static final String POST_COMMENT_URL = "http://tolet.artefactplus.com/comment.php";
    public static final String FETCH_COMMENT_URL = "http://tolet.artefactplus.com/populatecomment.php";
    public static final String LOGIN_URL = "http://tolet.artefactplus.com/login.php";
    public static final String FETCH_FEED_URL = "http://tolet.artefactplus.com/newsfeed.php";
    public static final String POST_FEED_URL = "http://tolet.artefactplus.com/post.php";
    public static final String PROFILE_TIMELINE_FEED_URL = "http://tolet.artefactplus.com/userpost.php";
    public static final String PROFILE_FETCH_PERSONAL_INFO = "http://tolet.artefactplus.com/showPersonalInfo.php";
    public static final String PROFILE_UPDATE_PERSONAL_INFO = "http://tolet.artefactplus.com/updatePersonalInfo.php";
    public static final String PROFILE_UPDATE_PASSWORD = "http://tolet.artefactplus.com/updatePassword.php";

}
