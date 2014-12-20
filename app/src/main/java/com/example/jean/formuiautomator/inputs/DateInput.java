package com.example.jean.formuiautomator.inputs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.formuiautomator.AppConstants;
import com.example.jean.formuiautomator.AppInstance;
import com.example.jean.formuiautomator.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by britzj on 13/11/2014.
 */
public class DateInput extends RelativeLayout {

    private TextView label;
    private EditText input;
    private ImageButton btnChooseDate;
    private DateInputListener listener;

    private DatePickerDialog dialog = null;
    private GregorianCalendar gregorianCalendar = AppInstance.getCalendar();
    private SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants._DATE_FORMAT);

    private String preExistingDate;
    private int initDay;
    private int initMonth;
    private int initYear;

    private String LOG_TAG = getClass().getSimpleName();

    public DateInput(Context context, String labelCaption) {
        super(context);
        final Context mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.
                                                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_date_input, this, true);
        label = (TextView) findViewById(R.id.tv_input_label);
        input = (EditText) findViewById(R.id.et_input_text);
        btnChooseDate = (ImageButton) findViewById(R.id.btn_choose_date);
        input.setImeActionLabel("Next", KeyEvent.KEYCODE_ENTER);
        label.setText(labelCaption);

        dateFormat.setTimeZone(gregorianCalendar.getTimeZone());

        btnChooseDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                preExistingDate = input.getText().toString().trim();
                Log.i(LOG_TAG, (label.getText() + " -> User tapped on Date Picker button"));

                GregorianCalendar dateTemp = null;
                if(preExistingDate != null && !preExistingDate.equals("")) {
                    // if the date field has some data in it, try to parse it and display in the
                    // date picker dialog.

                    dateTemp = parseStringToDate(preExistingDate);

                    initDay = dateTemp.get(Calendar.DAY_OF_MONTH);
                    initMonth = dateTemp.get(Calendar.MONTH);
                    initYear = dateTemp.get(Calendar.YEAR);

                }
                else {
                    // if the date field is empty or corrupt, then set the dialog to current date
                    Calendar cal = new GregorianCalendar();
                    if(dialog == null) {
                        initYear = cal.get(Calendar.YEAR);
                        initMonth = cal.get(Calendar.MONTH);
                        initDay = cal.get(Calendar.DAY_OF_MONTH);
                    }
                }
                dialog = new DatePickerDialog(mContext, new PickDate(), initYear, initMonth,
                                              initDay);
                dialog.show();
                Log.i(LOG_TAG, (label.getText() + " -> Date picker dialog is present."));
                if(listener != null) {
                    listener.onChooseDate(DateInput.this);
                }
            }
        });
    }

    public boolean validate() {
        return true;
    }

    public EditText getInput() {
        String temp = this.input.getText().toString().trim();
        this.input.setText(temp);
        return this.input;
    }

    public String getInputText () { return this.input.getText().toString(); }

    public void setInputText(String text) {
        this.input.setText(text);
    }

    public void setOnClickListener(DateInputListener listener) {
        this.listener = listener;
    }

    public interface DateInputListener {
        public void onChooseDate(View view);
    }

    private class PickDate implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            input.setText(parseDateToString(year, monthOfYear, dayOfMonth));
            view.updateDate(year, monthOfYear, dayOfMonth);
            dialog.hide();
            Log.i(LOG_TAG, (label.getText() + " -> Date picker dialog is closed."));
            Log.i(LOG_TAG, (label.getText() + " -> Date changed to : [ " + getInputText() + " ]."));
        }
    }

    public String parseDateToString(Calendar date)     {
        return dateFormat.format(date);
    }

    public String parseDateToString(int year, int monthOfYear, int dayOfMonth) {
        gregorianCalendar.set(year, monthOfYear, dayOfMonth);
        return dateFormat.format(gregorianCalendar.getTime());
    }

    public GregorianCalendar parseStringToDate(String dateInText) {

        try {
            gregorianCalendar.setTime(dateFormat.parse(dateInText));
        }
        catch(ParseException e) {
            Log.e(LOG_TAG, "ParseException: " + e.getMessage());
            if(!preExistingDate.equals("")) {

                Log.i(LOG_TAG, "Invalid date. Reverting to [ "
                        + dateFormat.format(gregorianCalendar.getTime()) + " ].");

                Toast.makeText(getContext(), "Invalid date. Reverting to \n"
                                + dateFormat.format(gregorianCalendar.getTime()),
                        Toast.LENGTH_SHORT).show();
            }


        }
        return gregorianCalendar;
    }
}
