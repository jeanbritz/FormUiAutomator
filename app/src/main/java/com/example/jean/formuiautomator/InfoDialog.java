package com.example.jean.formuiautomator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by BritzJ on 2015-03-08.
 */
public class InfoDialog extends DialogFragment {

    /*
     *  Init input parameter's tags
     */
    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";
    /**
     * Debug Tag for logging debug output to LogCat
     */
    private final String LOG_TAG = getClass().getSimpleName();
    /*
     *  Local instance variables
     */
    private String mTitle;
    private String mMessage;

    /*
     *  Callback instance
     */
    private InfoDialogListener mCallback;

    public InfoDialog() {
        // Required empty public constructor
    }

    /**
     * Use this function to construct and display this dialog.
     *
     * @param mTitle   Title of the dialog.
     * @param mMessage Message to be displayed within the dialog.
     * @return A new instance of fragment ConnectionErrorDialog.
     */

    public static InfoDialog newInstance(String mTitle, String mMessage) {
        InfoDialog fragment = new InfoDialog();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, mTitle);
        args.putString(ARG_MESSAGE, mMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreate() called");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mMessage = getArguments().getString(ARG_MESSAGE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        Log.v(LOG_TAG, "onAttach() called");
        super.onAttach(activity);
        try {
            mCallback = (InfoDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement InfoDialogListener");
        }
    }

    @Override
    public void onDetach() {
        Log.v(LOG_TAG, "onDetach() called");
        super.onDetach();
        // Detach the callback instance
        mCallback = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateDialog() called");
        String title = getArguments().getString(ARG_TITLE);
        String message = getArguments().getString(ARG_MESSAGE);

        return new AlertDialog.Builder(getActivity())
                //.setIcon(R.drawable.ic_alert_info)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mCallback != null) {
                            mCallback.onInfoDialogOK();
                        }
                    }
                })
                .create();
    }

    public interface InfoDialogListener {
        // Implement this method to control the 'OK' button on the dialog.
        public void onInfoDialogOK();

    }

}
