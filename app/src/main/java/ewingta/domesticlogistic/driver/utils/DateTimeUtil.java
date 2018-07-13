package ewingta.domesticlogistic.driver.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ewingta.domesticlogistic.driver.R;
import ewingta.domesticlogistic.driver.adapter.TimeAdapter;
import ewingta.domesticlogistic.driver.listeners.DateTimeSetListener;
import ewingta.domesticlogistic.driver.models.Time;

/**
 * Created by FAMILY on 14-12-2017.
 */

public class DateTimeUtil {
    public static final String FULL_DATE = "yyyy-MM-dd";
    public static final String yyyyMMdd = "yyyyMMdd";

    public void datePicker(final Context context, final DateTimeSetListener dateSetListener, final List<Time> times) {

        try {
            final Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            final int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            if (view.isShown()) {

                                monthOfYear = monthOfYear + 1;
                                String month = "" + monthOfYear;
                                String date = "" + dayOfMonth;

                                if (monthOfYear < 10) {
                                    month = "0" + monthOfYear;
                                }

                                if (dayOfMonth < 10) {
                                    date = "0" + dayOfMonth;
                                }

                                String fullDate = year + month + date;

                                final String parsedDate = convertDateStringFormat(fullDate, yyyyMMdd, FULL_DATE);

                                TimeAdapter timeAdapter = new TimeAdapter(context, times);

                                final Spinner sp = new Spinner(context);
                                sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                sp.setAdapter(timeAdapter);

                                AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
                                builder.setMessage(R.string.select_time);
                                builder.setView(sp);
                                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (dateSetListener != null) {
                                            Time time = (Time) sp.getSelectedItem();
                                            dateSetListener.onDateTimeSet(parsedDate, time);
                                        }
                                    }
                                });

                                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.create().show();
                            }
                        }
                    }, year, month, day);

            datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            datePickerDialog.show();
        } catch (Exception e) {
            CrashUtil.report(e);
        }
    }

    public String convertDateStringFormat(String dateValue, String fromFormat, String toFormat) {

        String dateValueNewFormat = null;

        try {

            Date date = getSimpleDateFormat(fromFormat).parse(dateValue);

            dateValueNewFormat = formatDateToString(date, toFormat);

        } catch (Exception e) {
            CrashUtil.report(e);
        }

        return dateValueNewFormat;
    }

    private SimpleDateFormat getSimpleDateFormat(String format) {
        return new SimpleDateFormat(format, Locale.US);
    }

    public String formatDateToString(Date date, String format) {

        String formattedDate = null;

        try {
            formattedDate = getSimpleDateFormat(format).format(date);
        } catch (Exception e) {
            CrashUtil.report(e);
        }

        return formattedDate;
    }

    public Date formatStringToDate(String dateValue, String format) {

        Date date = null;

        try {
            date = getSimpleDateFormat(format).parse(dateValue);
        } catch (Exception e) {
            CrashUtil.report(e);
        }

        return date;
    }

    public Date getDateWithoutTimeStamp(Date date) {

        Date dateWithoutTimeStamp = null;

        try {

            if (date != null) {

                Calendar cal = Calendar.getInstance();

                cal.setTime(date);

                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);

                dateWithoutTimeStamp = cal.getTime();
            }

        } catch (Exception e) {
            CrashUtil.report(e);
        }

        return dateWithoutTimeStamp;
    }

    public int getAge(Date date) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }


        return age;
    }
}
