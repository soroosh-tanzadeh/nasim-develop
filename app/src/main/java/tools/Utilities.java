package tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ir.maxivity.tasbih.LocationType;
import ir.maxivity.tasbih.R;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.models.IslamicDate;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;
import ir.mirrajabi.persiancalendar.helpers.DateConverter;
import okhttp3.MediaType;

public class Utilities {

    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static MediaType TEXT = MediaType.parse("text/plain");

    public static String numberConvert_Fa2En(@NonNull String rawStr) {
        StringBuffer stringBuffer = new StringBuffer();
        char c;
        for (int i = 0; i < rawStr.length(); i++) {
            c = rawStr.charAt(i);
            if (c <= '۹' && c >= '۰')
                stringBuffer.append((char) (c - '۰' + '0'));
            else
                stringBuffer.append(c);
        }
        return String.valueOf(stringBuffer);
    }


    // a mapping which used in numberConvert_En2Fa
    private static String[] faDigits = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};


    public static String numberConvert_En2Fa(@NonNull String rawStr) {
        if (rawStr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        char c;
        for (int i = 0; i < rawStr.length(); i++) {
            c = rawStr.charAt(i);
            if (c <= '9' && c >= '0')
                stringBuffer.append(faDigits[c - '0']);
            else
                stringBuffer.append(c);

        }
        return String.valueOf(stringBuffer);
    }


    public static <T> String createBody(T body) {
        Gson gson = new Gson();
        return gson.toJson(body);
    }

    public static Bitmap base64ToBitmap(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static int getMarkerOnType(LocationType type) {
        switch (type) {
            case COFFEESHOP:
                return R.drawable.ic_t_pin_coffeeshop;
            case HOSPITAL:
                return R.drawable.hospital_marker;
            case GYM:
                return R.drawable.gym_marker;
            case DENTISTRY:
                return R.drawable.dentistry_marker;
            case SHOP:
                return R.drawable.shop_marker;
            case HOTEL:
                return R.drawable.hotel_marker;
            case OTHER:
                return R.drawable.t_pin_coffeeshop;
            case UNIVERSITY:
                return R.drawable.university_marker;
            case MOSQUE:
                return R.drawable.mosque_marker;
            default:
                return R.drawable.marker2;
        }
    }

    public static LocationType getLocationType(int type) {
        switch (type) {
            case 1:
                return LocationType.COFFEESHOP;
            case 2:
                return LocationType.HOSPITAL;
            case 3:
                return LocationType.GYM;
            case 4:
                return LocationType.DENTISTRY;
            case 5:
                return LocationType.HOTEL;
            case 6:
                return LocationType.MOSQUE;
            case 7:
                return LocationType.UNIVERSITY;
            case 8:
                return LocationType.SHOP;
            default:
                return LocationType.OTHER;
        }
    }

    public static int getLocationType(Context context, String type) {
        if (context.getString(R.string.coffee_shop).equals(type)) {
            return 1;
        } else if (context.getString(R.string.hospital).equals(type)) {
            return 2;
        } else if (context.getString(R.string.gym).equals(type)) {
            return 3;
        } else if (context.getString(R.string.dentistry).equals(type)) {
            return 4;
        } else if (context.getString(R.string.hotel).equals(type)) {
            return 5;
        } else if (context.getString(R.string.mosque).equals(type)) {
            return 6;
        } else if (context.getString(R.string.university).equals(type)) {
            return 7;
        } else if (context.getString(R.string.shop).equals(type)) {
            return 8;
        } else {
            return 9;
        }
    }

    public static String getJalaliDate(Context context, PersianDate date) {
        PersianCalendarHandler calendar = PersianCalendarHandler.getInstance(context);
        String space = " ";

        return calendar.getWeekDayName(date) + space +
                calendar.formatNumber(date.getDayOfMonth()) + space +
                calendar.getMonthName(date);
    }

    public static String getTodayJalaliDate(Context context) {
        PersianCalendarHandler calendar = PersianCalendarHandler.getInstance(context);
        PersianDate date = calendar.getToday();
        String space = " ";

        return calendar.getWeekDayName(date) + space +
                calendar.formatNumber(date.getDayOfMonth()) + space +
                calendar.getMonthName(date);
    }

    public static String getJalaliDateRaw(Context context) {
        PersianCalendarHandler calendar = PersianCalendarHandler.getInstance(context);
        PersianDate date = calendar.getToday();
        String space = " ";

        return calendar.formatNumber(date.getDayOfMonth()) + space +
                calendar.getMonthName(date) + space + calendar.formatNumber(date.getYear());
    }

    public static String getNameOfToday(Context context) {
        PersianCalendarHandler calendar = PersianCalendarHandler.getInstance(context);
        PersianDate date = calendar.getToday();
        String space = " ";

        return calendar.getWeekDayName(date);
    }

    public static String getTodayIslamicDate(Context context) {
        PersianCalendarHandler calendar = PersianCalendarHandler.getInstance(context);
        PersianDate date = calendar.getToday();
        IslamicDate islamicDate = DateConverter.persianToIslamic(date);

        String[] iMonthNames = {"محرم", "صفر", "ربیع‌الاول",
                "ربیع‌الاخر", "جمادی‌الاول", "جمادی ‌الاخر", "رجب",
                "شعبان", "رمضان", "شوال", "ذی‌القعده", "ذی‌الحجه"};
        String space = " ";

        return calendar.formatNumber(islamicDate.getDayOfMonth() + "") + space +
                iMonthNames[islamicDate.getMonth() - 1] + space +
                islamicDate.getYear();

    }

    public static String getTodayGregortianDate(Context context) {
        /*PersianCalendarHandler calendar = PersianCalendarHandler.getInstance(context);
        PersianDate date = calendar.getToday();
        CivilDate civilDate = DateConverter.persianToCivil(date);
        String[] eMonthNames = {
                "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug","sep","oct","nov","dec"
        };

        return numberConvert_Fa2En(calendar.formatNumber(civilDate.getDayOfMonth() + " ") + " " +
                eMonthNames[civilDate.getMonth() - 1] + " " +
                civilDate.getYear());*/

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        return df.format(c);
    }

}
