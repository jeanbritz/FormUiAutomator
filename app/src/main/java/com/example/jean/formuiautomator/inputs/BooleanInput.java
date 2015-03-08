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
 * @since 1.0
 * @version 1.0
 *
 */
public class BooleanInput extends AbstractInput {

    private RadioGroup radioGroup;
    private RadioButton radioBtnYes;
    private RadioButton radioBtnNo;

    private int checkedOption;


    public BooleanInput(Context context) {
        super(context);

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

    /**
     * @return Either Yes or No
     */
    public String getInputText() {
        radioBtnYes = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        String temp = radioBtnYes.getText().toString();
        if(temp.equalsIgnoreCase("Yes")) {
            return "Yes";
        }
        else {
            return "No";
        }
    }

    /**
     * Still needs to be implemented
     * @return
     */
    @Override
    public boolean validate() {
        return false;
    }

    /**
     * Still needs to be implemented
     */
    @Override
    public void sanitize() {

    }
}