package com.example.jean.formuiautomator.inputs;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jean.formuiautomator.R;
import com.example.jean.formuiautomator.AppConstants;

/**
 * Created by BritzJ on 10/11/2014.
 */
public class ContactInput extends RelativeLayout implements TextView.OnEditorActionListener {

    private TextView label;
    private EditText input;
    private ImageButton btnChooseContact;
    private ContactInputListener listener;
    private boolean validInput = false;

    private String LOG_TAG = getClass().getSimpleName();

    public ContactInput(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_contact_input, this, true);
        label = (TextView) findViewById(R.id.tv_input_label);
        input = (EditText) findViewById(R.id.et_input_text);
        input.setOnEditorActionListener(this);
        btnChooseContact = (ImageButton) findViewById(R.id.btn_choose_contact);
        btnChooseContact.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG_TAG, (label.getText() + " -> User tapped on Contact Picker button"));
                if (listener != null) {
                    listener.onChooseContact(ContactInput.this);
                }
            }
        });
    }
    public ContactInput(Context context, String labelCaption) {
        this(context);
        label.setText(labelCaption);
    }
    // Future usage
    public ContactInput(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    // Future usage
    public ContactInput(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EditText getInput() {
        String temp = this.input.getText().toString().trim();
        this.input.setText(temp);
        return this.input;
    }

    public void sanitize() {
        String temp = getInput().getText().toString();
        if(temp.startsWith("+")) {
            temp = temp.substring(1, temp.length());

        }
        // check if number starts with a zero, if true replace it with the international code
        if(temp.startsWith("0")) {
            temp = temp.replaceFirst("^0", "27");
        }
        // clear all whitespaces between digits
        temp = temp.replaceAll("\\s+", "");
        setInputText(temp);
    }

    public boolean hasValidInput() { return this.validInput; }

    public String getInputText() {
        return this.input.getText().toString();
    }

    public void setInputText(String text) {
        this.input.setText(text);
    }

    public boolean validate () {

        // basic length check
        if(getInputText().length() >= 10 && getInputText().length() < 12) {
            validInput = true;
            return true;
        }
        else {
            Log.w(LOG_TAG, (label.getText() + " -> User entered an invalid number [ "
                                            + getInputText() + " ]"));
            getInput().setError("Invalid number");
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        Log.d(LOG_TAG, "onEditorAction called [ actionId : " + actionId + " ] ");
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            // do validation
            Log.i(LOG_TAG, (label.getText() + " -> IME action button tapped."));
            validInput = false;
            return validate();
        }
        return false;
    }

    public void setOnClickListener(ContactInputListener listener) {
        this.listener = listener;
    }

    public interface ContactInputListener {
        public void onChooseContact(View view);
    }
}
