package com.example.jean.formuiautomator.inputs;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jean.formuiautomator.R;

/**
 * Created by BritzJ on 10/11/2014.
 */
public class ContactInput extends AbstractInput implements TextView.OnEditorActionListener {

    private ImageButton btnChooseContact;
    private ContactInputListener listener;
    private boolean validInput = false;

    private String LOG_TAG = getClass().getSimpleName();

    public ContactInput(Context context) {
        super(context);

        inflater.inflate(R.layout.view_contact_input, this, true);
        label = (TextView) findViewById(R.id.tv_input_label);
        input = (EditText) findViewById(R.id.et_input_text);

        btnChooseContact = (ImageButton) findViewById(R.id.btn_choose_contact);
        btnChooseContact.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG_TAG, (label.getText() + "User tapped on Contact Picker button"));
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

    public EditText getInput() {
        String temp = ((EditText) input).getText().toString().trim();
        ((EditText) input).setText(temp);
        return ((EditText) input);
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


    public boolean validate () {

        // basic length check
        if (getInput().length() >= 10 && getInput().length() < 12) {
            validInput = true;
            return true;
        }
        else {
            Log.w(LOG_TAG, (label.getText() + " -> User entered an invalid number [ "
                    + getInput() + " ]"));
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
