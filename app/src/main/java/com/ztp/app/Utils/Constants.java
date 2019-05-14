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
    public static int NewRequestVol = 10;
    public static int WithdrawnVol = 90;
    public static int AcceptedByCSO = 20;
    public static int DeclinedByCSO = 30;
    public static int CompletedByVol = 40;
    public static int MoreInfoByCSO = 50;
    public static int RejectedCompleteByCSO = 60;
    public static int CompletedVerifiedByCSO = 70;
    public static String Volunter_apply_0 = "0";
    public static String Volunter_apply_1 = "1";
}
