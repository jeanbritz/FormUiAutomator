package com.example.jean.formuiautomator.inputs;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jean.formuiautomator.R;


/**
 * Boolean input mask that displays two radio options labeled as Yes and No
 *
 * @author britzj
 * @version 1.0
 * @since 1.0
 */
public class BooleanInput extends AbstractInput {

    public BooleanInput(Context context) {
        super(context);
        inflater.inflate(R.layout.view_boolean_input, this, true);
        label = (TextView) findViewById(R.id.tv_input_label);
        input = findViewById(R.id.radio_grp_boolean_input);
    }

    public BooleanInput(Context context, String labelCaption) {
        this(context);
        label.setText(labelCaption);
    }

    public boolean hasValidInput() {
        return true;
    }


    public String sanitize(String s) {
        return s;
    }

    public RadioGroup getInput() {
        return ((RadioGroup) input);
    }

    /**
     * @return Either Yes or No
     */
    public String getInputText() {
        RadioButton temp = (RadioButton) findViewById(getInput().getCheckedRadioButtonId());
        return temp.getText().toString();

    }


}