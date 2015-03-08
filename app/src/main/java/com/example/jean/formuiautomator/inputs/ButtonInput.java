package com.example.jean.formuiautomator.inputs;

import android.content.Context;
import android.widget.Button;

import com.example.jean.formuiautomator.R;

/**
 * Created by BritzJ on 2015-03-08.
 */
public class ButtonInput extends AbstractInput {

    private String key;

    public ButtonInput(Context context) {
        super(context);
        inflater.inflate(R.layout.view_button_input, this, true);
        input = findViewById(R.id.btn_input);

    }

    public ButtonInput(Context context, String buttonCaption, String key) {
        this(context);
        getInput().setText(buttonCaption);
        this.key = key;
    }


    public Button getInput() {
        return ((Button) input);
    }


    public String getInputText() {
        return key;
    }


    public boolean hasValidInput() {
        return false;
    }


    public String sanitize(String s) {
        return null;
    }


    // Coupling the button with onClickListener

    public void setOnClickListener(OnClickListener l) {
        input.setOnClickListener(l);
    }

    public int getId() {
        return input.getId();
    }

    public void setId(int id) {
        input.setId(id);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setAlignment() {

    }


}
