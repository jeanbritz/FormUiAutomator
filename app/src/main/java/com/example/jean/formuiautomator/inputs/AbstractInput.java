package com.example.jean.formuiautomator.inputs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Abstract class for all the custom input masks used in the application
 *
 * @author britzj
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractInput extends RelativeLayout {

    protected LayoutInflater inflater;
    protected TextView label;
    protected View input;
    /**
     * Log tag for LogCat
     */
    String LOG_TAG = getClass().getSimpleName();

    public AbstractInput(Context context) {
        super(context);
        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /**
     * Default constructor that is called from the super class {@link android.widget.RelativeLayout}
     *
     * @param context
     * @param attrs
     */

    public AbstractInput(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @return The label reference object for the input mask of type {@link android.widget.TextView}
     */
    public TextView getLabel() {
        return this.label;
    }

    /**
     * @param textView
     */
    public void setLabel(TextView textView) {
        this.label = textView;
    }

    /**
     * Sets the label's text for the input mask
     *
     * @param labelText
     */
    public void setLabelText(String labelText) {
        this.label.setText(labelText);
    }

    /**
     * @return The input mask's input object reference. This function is typically called
     * when extracting the data from all types of input masks
     */
    public View getInput() {
        return this.input;
    }

    /**
     * @param inputView
     */
    public void setInput(View inputView) {
        this.input = inputView;
    }

    /**
     * @param inputText
     */
    public void setInputText(String inputText) {

    }

    /**
     * Validation check
     * <p/>
     * This method evaluates the input text (typically located in EditText, RadioButton, CheckBox)
     * whether it is a valid input given the design constraints
     *
     * @return true - if input value passes all validation tests; false - if input value fails one
     * of the validation tests
     */
    public abstract boolean validate();

    /**
     * Sanitising filter
     */
    public abstract void sanitize();
}
