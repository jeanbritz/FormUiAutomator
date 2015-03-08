package com.example.jean.formuiautomator.inputs;

import android.content.Context;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jean.formuiautomator.AppConstants;
import com.example.jean.formuiautomator.R;

/**
 * Created by BritzJ on 10/11/2014.
 */
public class TextInput extends AbstractInput {

    private boolean validInput = false;

    public TextInput(Context context) {
        super(context);

        inflater.inflate(R.layout.view_text_input, this, true);

        label = (TextView) findViewById(R.id.tv_input_label);
        input = (EditText) findViewById(R.id.et_input_text);
        ((EditText) input).setImeActionLabel("Next", KeyEvent.KEYCODE_ENTER);

    }
    public TextInput(Context context, String labelCaption) {
        this(context);
        label.setText(labelCaption);
    }
    public TextInput(Context context, String labelCaption, String inputMask) {
        this(context, labelCaption);

        // Setting the input type of the text input field
        if(inputMask.equalsIgnoreCase(AppConstants._NUMBER_INPUT)) {
            ((EditText) input).setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        else if (inputMask.equalsIgnoreCase(AppConstants._PASSWORD_INPUT)) {
            ((EditText) input).setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ((EditText) input).setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        if(inputMask.equalsIgnoreCase(AppConstants._PLAIN_TEXT_INPUT)) {
            ((EditText) input).setInputType(InputType.TYPE_CLASS_TEXT);
        }

    }

    public TextInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_text_input, this, true);
        label = (TextView) findViewById(R.id.tv_input_label);
        input = (EditText) findViewById(R.id.et_input_text);
    }

    public boolean validate() {

        // Simple validation. Check if variable is not null.
        if (getInput().length() > 0 && !getInput().equals("null")) {
            return true;
        }
        return false;
    }

    @Override
    public void sanitize() {

    }

    public EditText getInput() {

        String temp = ((EditText) input).getText().toString().trim();
        ((EditText) input).setText(temp);
        return ((EditText) input);
    }

    public boolean hasValidInput() {
        return this.validate();
    }
}