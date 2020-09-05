package in.afckstechnologies.afcksmaintenanceapp.utils;

import java.util.ArrayList;



public class Config {

    public static final String BASE_URL = "http://afckstechnologies.in/afcks/api/";

    public static final String URL_BOOKING_BATCH = BASE_URL + "user/bookingbatchByAdmin";

    public static final String URL_AVAILABLEENQUIRYUSER = BASE_URL + "user/availableEnquiryUser";
    public static final String URL_UPDATEMAINTENANCEDETAILS = BASE_URL + "user/updatemaintenancedetails";
    public static final String URL_ADDABRANDNAME = BASE_URL + "user/addABrandName";
    public static final String URL_GETALLAITEMNAME = BASE_URL + "user/getallAItemName";
    public static final String URL_GETALLABRANDNAME = BASE_URL + "user/getallABrandName";
    public static final String URL_ADDAITEMNAME = BASE_URL + "user/addAItemName";
    public static final String URL_GETALLACCESSORYDETAILS = BASE_URL + "user/getallAccessoryDetails";
    public static final String URL_ADDMOREACCESSORYDETAILS = BASE_URL + "user/addMoreAccessoryDetails";
    public static final String URL_GETACCESSORYDETAILS = BASE_URL + "user/getAccessoryDetails";
    public static final String URL_GETALLFITEMNAME = BASE_URL + "user/getallFItemName";
    public static final String URL_GETALLFLOCATIONNAME = BASE_URL + "user/getallFLocationName";
    public static final String URL_GETALLFSTATUSNAME = BASE_URL + "user/getallFStatusName";
    public static final String URL_GETALLVIEWACCESSORIES = BASE_URL + "user/getallViewAccessories";
    public static final String URL_GETALLANTIKEYSDETAILSBYPREFIX = BASE_URL + "user/getallAntiKeysDetailsByPreFix";
    public static final String URL_UPDATEANTIVAIRUSDETALS = BASE_URL + "user/updateAntivairusDetals";
    public static final String URL_UPDATECALLBACKSTUDENT = BASE_URL + "user/updateCallbackStudent";
    public static final String URL_ADDMOREASSESTSDETAILS = BASE_URL + "user/addMoreAssestsDetails";
    public static final String URL_ADDIDENTIFICATIONDETAILS = BASE_URL + "user/addIdentificationDetails";
    public static final String URL_UPDATEIDENTIFICATIONDETAILS = BASE_URL + "user/updateidentificationdetails";
    public static final String URL_GETALLFIXEDASSESTS = BASE_URL + "user/getallFixedAssests";
    public static final String URL_ADDFLOCATIONNAME = BASE_URL + "user/addFLocationName";
    public static final String URL_ADDFITEMNAME = BASE_URL + "user/addFItemName";
    public static final String URL_GETUSERNAMEPASSSMS = BASE_URL + "user/getUserNamePassSMS";
    public static final String URL_GETAVAILABLEMOBILEDEVICES = BASE_URL + "user/getAvailableMAppsMobileDeviceID";
    public static final String URL_GETAVAILABLEROLES = BASE_URL + "user/getAvailableRoles";

    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "AFCKS Images";
    public static String DATA_ENTERLEVEL_COURSES = "";
    public static String DATA_SPLIZATION_COURSES = "";

    public static String DATA_MOVE_FROM_LOCATION = "";
    public static ArrayList<String> VALUE = new ArrayList<String>();
    // public static final String SMS_ORIGIN = "WAVARM";
    public static final String SMS_ORIGIN = "AFCKST";
}
