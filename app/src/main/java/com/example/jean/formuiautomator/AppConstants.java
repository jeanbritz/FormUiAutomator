package com.example.jean.formuiautomator;

/**
 * Created by Jean on 11-Dec-14.
 */
public class AppConstants {

    /**
     * Date format used within this project
     */
    public static final String _TIME_ZONE = "GMT+2";
    public static final String _DATE_FORMAT = "dd/MMM/yyyy";

    /**
     * Input mask types
     */
    public static final String _BOOLEAN_INPUT = "boolean";
    public static final String _DATE_INPUT = "date";
    public static final String _CONTACT_INPUT = "contact";
    public static final String _PASSWORD_INPUT = "password";
    public static final String _NUMBER_INPUT = "number";
    public static final String _PLAIN_TEXT_INPUT = "string";


    /**
     * Test data
     */
    public static final String TEST_FORM_DATA_JSON = "[{\"FIRST_NAME\":{\"First Name\":\"string\"}}," +
            "{\"BIRTHDAY\":{\"Birthday\":\"date\"}}," +
            "{\"MOBILE_NUMBER\":{\"Mobile Number\":\"contact\"}}," +
            "{\"AGREE_TC\": {\"Do you agree with the T&C?\":\"boolean\"}}]";
}
