package ucw.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ucw.pages.BasePage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper extends BasePage {
    private static final Logger logger = LogManager.getLogger(DateHelper.class);
    private static final DateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat systemClockFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final Calendar calendar = Calendar.getInstance();

    public static String getTodayWeekDay() {
        String weekDay = DayOfWeek.from(LocalDate.now()).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        logger.info("The weekday for today is " + weekDay);
        return weekDay;
    }

    public static String getTodayDay() {
        String date = getCurrentDate();
        logger.info("Today's Date is "+ date);
        String todayDay = getDayForDate(date);
        logger.info("The Day for the Today's Date is " + todayDay);
        return todayDay;
    }

    public static String getSimpleToday() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(currentDate);
    }

    private static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return dtFormat.format(cal.getTime());
    }

    private static String getDayForDate(String dateVal) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(df.parse(dateVal));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat day_sdf = new SimpleDateFormat("EEEEEEEEE");
        return day_sdf.format(cal.getTime());
    }

    public static int getNoOfYears(String inputData) {
        int diffYear = 0;

        try {
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
            String dateString = myFormat.format(date);
            Date regDateBefore = myFormat.parse(inputData);// converting string to Date
            Date dateAfter = myFormat.parse(dateString);// converting string to Date
            Calendar c = Calendar.getInstance();
            c.setTime(regDateBefore); // Setting the value passed in method's date
            int inputYear = c.get(Calendar.YEAR);
            logger.info("Year in " + inputData + " is " + inputYear);
            c.setTime(dateAfter);
            int todayYear = BasePage.systemFutureYear;
            logger.info("Current year is " + todayYear);
            diffYear = todayYear - inputYear;
            logger.info("Number of years calculated between given input date and today's date is " + diffYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return diffYear;
    }

    public static void setVirtualDatePortal(By byElement, String value) {
        WebElement elem = driver.findElement(byElement);
        String date = value.replaceAll("-", "/");
        int yrCalc = Integer.parseInt(date.split("/")[2]);
        if (yrCalc > 20 && yrCalc < 99) {
            date = date.split("/")[0] + "/" + date.split("/")[1] + "/" + "19" + date.split("/")[2];
        } else if (yrCalc > 1 && yrCalc <= 20) {
            date = date.split("/")[0] + "/" + date.split("/")[1] + "/" + "20" + date.split("/")[2];
        }
        elem.clear();
        elem.sendKeys(date);
        elem.isEnabled();
    }

    public static String getDiffBetweenTwoDays(String startDate, String endDate) {
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = dtFormat.parse(startDate);
            d2 = dtFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert d2 != null;
        int diff = (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
        return String.valueOf(diff);
    }

    public static String getFormattedDate(String date) {
        Date actualDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        try {
            actualDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(actualDate);
        logger.info("The final date after conversion is: " + date);
        return formattedDate;
    }

    public static String getRandomDateInYearRange(int startYear, int endYear) {
        return new RandomDate(
                LocalDate.of(startYear, 12, 30),
                LocalDate.of(endYear, 1, 1)).nextDate();
    }

    public static String getPastDate(String dateString, int day) {
        LocalDate pastDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy")).minusDays(day);
        return pastDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String getCurrentDateWithFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(LocalDate.now());
    }

    public static String addHourToSystemClock(String currentTime, int addHour) {
        try {
            calendar.setTime(systemClockFormat.parse(currentTime));
            calendar.add(Calendar.HOUR, addHour);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return systemClockFormat.format(calendar.getTime());
    }

    public static boolean isFutureDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).isAfter(LocalDate.now());
    }
}
