package ir.maxivity.tasbih;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.widget.RemoteViews;

import com.azan.Azan;
import com.azan.AzanTimes;
import com.azan.Method;
import com.azan.Time;
import com.azan.astrologicalCalc.SimpleDate;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import tools.Utilities;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    public static final double iranDefaultLat = 32.4279;
    public static final double iranDefaultLon = 53.6880;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        initViews(context, views);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void initViews(Context context, RemoteViews view) {
        String jalaliDate = Utilities.getJalaliDateRaw(context);
        String dayOfName = Utilities.getNameOfToday(context);
        String arabicDate = Utilities.getTodayIslamicDate(context);
        String gregorianDate = Utilities.getTodayGregortianDate(context);
        view.setTextViewText(R.id.jalali_date, jalaliDate);
        view.setTextViewText(R.id.name_of_day, dayOfName);
        view.setTextViewText(R.id.arabic_date, arabicDate);
        view.setTextViewText(R.id.gregorian_date, gregorianDate);

        AzanTimes azanTimes = configureAzanTimes(context);

        String azanSobh = azanTimes.fajr().toString();
        String azanZohr = azanTimes.thuhr().toString();
        Time maghribAzan = azanTimes.maghrib();
        maghribAzan.setMinute(maghribAzan.getMinute() + 17);
        String azanMaqrib = maghribAzan.toString();
        String sunRise = azanTimes.shuruq().toString();
        view.setTextViewText(R.id.azan_sobh, Utilities.numberConvert_En2Fa(azanSobh));
        view.setTextViewText(R.id.azan_zohr, Utilities.numberConvert_En2Fa(azanZohr));
        view.setTextViewText(R.id.azan_maqrib, Utilities.numberConvert_En2Fa(azanMaqrib));
        view.setTextViewText(R.id.sunrise, Utilities.numberConvert_En2Fa(sunRise));

        view.setOnClickPendingIntent(R.id.layout_wrapper, getPendingIntent(context));

    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, FullScreenSplash.class);
        return PendingIntent.getActivity(context, 100, intent, 0);
    }

    private static AzanTimes configureAzanTimes(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NasimApplication.MAIN_PREF_NAME, Context.MODE_PRIVATE);
        String userLocation = sharedPreferences.getString(NasimApplication.PREF_USER_LOCATION_KEY, null);
        Location location;
        if (userLocation != null) {
            location = new Location("loc");
            location.setLatitude(Double.parseDouble(userLocation.split(",")[0]));
            location.setLongitude(Double.parseDouble(userLocation.split(",")[1]));
        } else {
            location = new Location("loc");
            location.setLatitude(iranDefaultLat);
            location.setLongitude(iranDefaultLon);
        }


        Calendar calendar = PersianCalendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        double gmtDiff = 4.5;
        com.azan.astrologicalCalc.Location azanLocation = new com.azan.astrologicalCalc.Location(location.getLatitude()
                , location.getLongitude(), gmtDiff, 0);
        SimpleDate today = new SimpleDate(new GregorianCalendar());
        /*DateComponents date = DateComponents.from(new Date());
        Coordinates coordinates = new Coordinates(location.getLatitude(), location.getLongitude());
        CalculationParameters params =
                CalculationMethod.KARACHI.getParameters();
        params.fajrAngle = 17.7;
        params.ishaAngle = 14;
        PrayerTimes prayerTimes = new PrayerTimes(coordinates,  ate, params);
*/
        Method tehranGeophysics = Method.Companion.getKARACHI_SHAF();
        tehranGeophysics.setIshaaAng(14);
        tehranGeophysics.setFajrAng(17.7);
        tehranGeophysics.setMaghribOffset(17);
        Azan azan = new Azan(azanLocation, tehranGeophysics);
        return azan.getPrayerTimes(today);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

