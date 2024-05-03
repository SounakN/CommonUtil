package utilities;


import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateAndTimeHandler {

    public static String getCurrentDateAndTime() {
        String pattern = "dd/MM/YYYY HH:mm:SS";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String currentDate = "";
        currentDate = sdf.format(new Date());
        return currentDate;
    }

    public static String getNextDateAndTime(int days) {
        String nextDate = "";
        String pattern = "dd/MM/YYYY HH:mm:SS";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, days);
        nextDate = sdf.format(cal.getTime());

        return nextDate;
    }

    public static String getDate(int days) {
        String PreviousDate = "";
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, days);
        PreviousDate = sdf.format(cal.getTime());

        return PreviousDate;

    }

    public static String getDateAsPerPattern(int days) {
        String PreviousDate = "";
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, days);
        PreviousDate = sdf.format(cal.getTime());

        return PreviousDate;

    }

    public static String getDateAndTime() {
        String nextDate = "";
        String pattern = "dd-MM-yyyy HH:mm:SS";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        nextDate = sdf.format(new Date());

        return nextDate;
    }

    public static String getMonthName(int monthIndex) {
        return new DateFormatSymbols().getMonths()[monthIndex - 1].toString();
    }

    public static void main(String[] args) {

        System.out.println("date::" + getDate(+7));
        //System.out.println(localTimePlusMinutes());
        //System.out.println(localTimePlusHours());

    }

    public static String timeStamp() {
        LocalDateTime dateTime = LocalDateTime.now();
        String timeStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
        return timeStamp;
    }

    public static String timeStamp_pay_start() {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(2);
        String timeStampDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(dateTime);
        String timeStampTime = DateTimeFormatter.ofPattern("HH:mm:ss").format(dateTime);

        String timeStamp_pay = timeStampDate + "T" + timeStampTime + "Z";
        return timeStamp_pay;
    }

    public static String timeStamp_pay_end() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(2);
        String timeStampDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(dateTime);
        String timeStampTime = DateTimeFormatter.ofPattern("HH:mm:ss").format(dateTime);

        String timeStamp_pay = timeStampDate + "T" + timeStampTime + "Z";
        return timeStamp_pay;
    }

    public static String UTCTimeStamp(int day) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        String output = sdf.format(c.getTime());
        System.out.println("Extra days: " + output);
        return output;
    }

    public static String UTCTimeStampMinutes(int day, int minute) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        c.add(Calendar.MINUTE, minute);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        String output = sdf.format(c.getTime());
        System.out.println("Extra days: " + output);
        return output;
    }

    public static String localTimePlusMinutes() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, 25);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        date = c.getTime();
        String dateTime = sdf.format(date);
        return dateTime;
    }

    public static String localTimePlusHours() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, 2);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        date = c.getTime();
        String dateTime = sdf.format(date);
        return dateTime;
    }

}