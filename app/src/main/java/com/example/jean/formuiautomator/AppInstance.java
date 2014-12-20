package com.example.jean.formuiautomator;

import android.app.Application;
import android.content.Context;

import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Jean on 11-Dec-14.
 */
public class AppInstance extends Application {

    public static final String LOG_TAG = AppInstance.class.getSimpleName();

    private static AppInstance mAppInstance;
    private static Context mContext;

    public AppInstance() {
        /**
         *  Mandatory default constructor
         */
    }

    private AppInstance(Context context) {
        this.mContext = context;
    }

    public static synchronized AppInstance getInstance(Context context) {
        if(mAppInstance == null) {
            mAppInstance = new AppInstance(context);
        }
        return mAppInstance;
    }

    /**
     * Just a method which calls the global calendar, because when you are dealing with
     * date and time it is important to refer to the same calendar.
     *
     * @return A Gregorian calendar with the timezone specfied.
     */
    public static GregorianCalendar getCalendar() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getTimeZone(AppConstants._TIME_ZONE));
        return cal;
    }
}
