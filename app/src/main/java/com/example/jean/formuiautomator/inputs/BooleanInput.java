package com.example.jean.formuiautomator.inputs;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jean.formuiautomator.R;

/**
 * Created by britzj on 12/11/2014.
 */
public class BooleanInput extends RelativeLayout {

    private TextView label;
    private RadioGroup radioGroup;
    private RadioButton radioBtnYes;
    private RadioButton radioBtnNo;

    private int checkedOption;

    private String LOG_TAG = getClass().getSimpleName();

    public BooleanInput(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_boolean_input, this, true);

        label = (TextView) findViewById(R.id.tv_input_label);
        radioGroup = (RadioGroup) findViewById(R.id.radio_grp_boolean_input);
        radioBtnYes = (RadioButton) findViewById(R.id.radio_btn_yes);
        radioBtnNo = (RadioButton) findViewById(R.id.radio_btn_no);

    }

    public BooleanInput(Context context, String labelCaption) {
        this(context);
        label.setText(labelCaption);

    }

    public String getInput() {
        radioBtnYes = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        String temp = radioBtnYes.getText().toString();
        if(temp.equalsIgnoreCase("Yes")) {
            return "Yes";
        }
        else {
            return "No";
        }
    }
}
