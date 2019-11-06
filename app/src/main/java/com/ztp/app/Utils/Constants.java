package com.ztp.app.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;


public class Constants {


    public static int cal = 0;
    public static float alpha = 0.4f;
    public static float no_alpha = 1.0f;
    public static DateFormat ff = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
    public static String user_type_student = "stu";
    public static String user_type_cso = "cso";
    public static String user_type_volunteer = "vol";
    public static String user_type_csa = "csa";

    public static int NewRequestVol = 10;
    public static int WithdrawnVol = 90;
    public static int AcceptedByCSO = 20;
    public static int DeclinedByCSO = 30;
    public static int CompletedByVol = 40;
    public static int MoreInfoByCSO = 50;
    public static int MoreInfoCSO = 51;
    public static int RejectedCompleteByCSO = 60;
    public static int CompletedVerifiedByCSO = 70;

    public static String Volunter_apply_0 = "0";
    public static String Volunter_apply_1 = "1";
    public static String APP_ID = "83F1DA53-7B63-4707-8B43-CB42349FDD4F";
    public static boolean backFromChat = false;
    public static boolean group = false;
    public static String download = "download";
    public static String delete = "delete";
    public static String Certificate501C3_Name = "501C3 Certificate";
    public static String SENDBIRD_CHANNEL = "Channel";
    public static String SENDBIRD_INDIVIDUAL = "Individual";

    public static String WITHDRAWN = "Withdrawn";
    public static String NOT_AVAILABLE = "Not Available";
    public static String PENDING = "Waiting";
    public static String ACCEPTED = "Accepted";
    public static String DECLINED = "Declined";
    public static String COMPLETED = "Completed";
    public static String MORE_INFO = "More Info";
    public static String REJECTED = "Rejected";
    public static String VERIFIED = "Verified";
    public static String AVAILABLE = "Available";
    //FLAVOURS
    public static String FLAVOUR_BLUEPRINT = "blueprint";
    public static String FLAVOUR_THUMBPRINT = "thumbprint";
    public static String FLAVOUR_PRODUCTION = "production";

}
