package com.example.jean.formuiautomator.inputs;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jean.formuiautomator.AppConstants;
import com.example.jean.formuiautomator.R;

/**
 * Created by BritzJ on 10/11/2014.
 */
public class TextInput extends AbstractInput {

    public TextInput(Context context) {
        super(context);

        inflater.inflate(R.layout.view_text_input, this, true);

        label = (TextView) findViewById(R.id.tv_input_label);
        input = findViewById(R.id.et_input_text);
        getInput().setImeActionLabel("Next", KeyEvent.KEYCODE_ENTER);

    }

    public TextInput(Context context, String labelCaption) {
        this(context);
        label.setText(labelCaption);
    }

    public TextInput(Context context, String labelCaption, String inputMask) {
        this(context, labelCaption);

        // Setting the input type of the text input field
        if (inputMask.equalsIgnoreCase(AppConstants._NUMBER_INPUT)) {
            getInput().setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (inputMask.equalsIgnoreCase(AppConstants._PASSWORD_INPUT)) {
            getInput().setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            getInput().setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        if (inputMask.equalsIgnoreCase(AppConstants._PLAIN_TEXT_INPUT)) {
            getInput().setInputType(InputType.TYPE_CLASS_TEXT);
        }

    }

    public EditText getInput() {
        return ((EditText) input);
    }

    public String getInputText() {
        return getInput().getText().toString();
    }

    public boolean hasValidInput() {
        getInput().setError(null);
        if (!TextUtils.isEmpty(getInput().getText().toString())) {
            return true;
        }
        getInput().setError("Text is required");
        return false;
    }

    @Override
    public String sanitize(String s) {
        return s.trim();
    }
}