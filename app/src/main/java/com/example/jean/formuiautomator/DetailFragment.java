package com.example.jean.formuiautomator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.jean.formuiautomator.inputs.AbstractInput;
import com.example.jean.formuiautomator.inputs.BooleanInput;
import com.example.jean.formuiautomator.inputs.ButtonInput;
import com.example.jean.formuiautomator.inputs.ContactInput;
import com.example.jean.formuiautomator.inputs.DateInput;
import com.example.jean.formuiautomator.inputs.TextInput;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.DetailFragmentListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private static final String ARG_ACTION_BAR_TITLE = "formTitle";
    private static final String ARG_FORM_DATA_JSON = "formJson";
    /**
     *  Debug tag for logging debug output to LogCat
     */
    private final String LOG_TAG = getClass().getSimpleName();
    private final int PICK_CONTACT_REQUEST = 100;
    String returnVars[];
    int validationErrors = 0;
    private String mActionBarTitle;
    private String mFormDataJson;
    private DetailFragmentListener mCallback;
    private View auxView;
    private List<AbstractInput> inputArrayList;

    public DetailFragment() {
        inputArrayList = new ArrayList<AbstractInput>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mTitle        Title set in ActionBar.
     * @param mFormDataJson JSON-formatted data used to generated the form with.
     * @return A new instance of fragment DetailFragment.
     */
    //
    public static DetailFragment newInstance(String mTitle, String mFormDataJson) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACTION_BAR_TITLE, mTitle);
        args.putString(ARG_FORM_DATA_JSON, mFormDataJson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mActionBarTitle = getArguments().getString(ARG_ACTION_BAR_TITLE);
            mFormDataJson = getArguments().getString(ARG_FORM_DATA_JSON);
        }
        getActivity().setTitle(mActionBarTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Context context = getActivity();

        // hides the keyboard when form is displayed
        getActivity().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        // JSON auxiliary objects
        JSONArray inputArray = null;
        JSONArray inputsJsonArray = null;
        JSONObject inputsJsonObject = null;
        JSONObject inputMaskData = null;

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // Reference object used when instantiating a new input object
        AbstractInput newInput = null;

        try {
            inputsJsonArray = new JSONArray(mFormDataJson);
            returnVars = new String[inputsJsonArray.length()];
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
            returnVars = new String[0];
        }

        for (int i = 0; i < inputsJsonArray.length(); i++) {
            String inputMaskType = "";
            String inputLabelText = "";
            try {
                inputsJsonObject = inputsJsonArray.getJSONObject(i);
                returnVars[i] = inputsJsonObject.keys().next().toString();
                inputMaskData = inputsJsonObject.getJSONObject(returnVars[i]);
                inputLabelText = inputMaskData.keys().next().toString();
                inputMaskType = inputMaskData.getString(inputLabelText);

            }
            catch (JSONException e) {
                Log.e(LOG_TAG, "JSONException: "+ e.getMessage());
            }

                if (inputMaskType.equalsIgnoreCase(AppConstants._BOOLEAN_INPUT)) {
                    newInput = new BooleanInput(context, inputLabelText);

                } else if (inputMaskType.equalsIgnoreCase(AppConstants._CONTACT_INPUT)) {
                    newInput = new ContactInput(context, inputLabelText);
                    ((ContactInput) newInput).setOnClickListener(new ContactInput
                            .ContactInputListener() {
                        @Override
                        public void onChooseContact(View view) {
                            auxView = view;
                            Intent pickContactIntent = new Intent(Intent.ACTION_PICK,
                                    Uri.parse("content://contacts"));
                            pickContactIntent.setType(ContactsContract.CommonDataKinds
                                    .Phone.CONTENT_TYPE);
                            startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
                        }
                    });

                } else if (inputMaskType.equalsIgnoreCase(AppConstants._DATE_INPUT)) {
                    newInput = new DateInput(context, inputLabelText);
                    ((DateInput) newInput).setOnClickListener(new DateInput.DateInputListener() {
                        @Override
                        public void onChooseDate(View view) {
                            auxView = view;

                        }
                    });

                } else if (inputMaskType.equalsIgnoreCase(AppConstants._PASSWORD_INPUT)) {
                    newInput = new TextInput(context, inputLabelText,
                            AppConstants._PASSWORD_INPUT);

                } else if (inputMaskType.equalsIgnoreCase(AppConstants._NUMBER_INPUT)) {
                    newInput = new TextInput(context, inputLabelText, AppConstants._NUMBER_INPUT);

                } else {
                    newInput = new TextInput(context, inputLabelText);
                }
            linearLayout.addView(newInput, params);
            inputArrayList.add(newInput);
            Log.d(LOG_TAG, "Adding " + inputMaskType + " input labeled : [ "
                    + inputLabelText + " ]");

        }
        newInput = new ButtonInput(context, "Submit", "submit");
        newInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String output = extractInputData().toString();
                if (validationErrors > 0) {
                    InfoDialog.newInstance("Attention", "Validation errors occurred in the form. " +
                            "Please check and try submitting them again.").show(getFragmentManager(),
                            "Attention");
                    validationErrors = 0;
                } else {
                    InfoDialog.newInstance("Submitted", output).show(getFragmentManager(),
                            "Submitted");
                }
                Log.i(LOG_TAG, output);

            }
        });
        linearLayout.addView(newInput);

        // ScrollView adds scroll bars to the linear layout if all the views does not fit on the
        // screen.
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(linearLayout);
        container.addView(scrollView);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (DetailFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onDetailFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(LOG_TAG, "onActivityResult() called");
        // First check which request we have to respond to

        switch(requestCode) {
            case PICK_CONTACT_REQUEST:
                if(resultCode == getActivity().RESULT_OK) {
                    // The user picked a contact.
                    // The intent's data Uri identifies which contact was selected

                    // Get the URI that points to the selected contact
                    Uri contactUri = data.getData();

                    // Select the columns
                    String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER};

                    Cursor cursor = getActivity().getContentResolver().query(contactUri, projection,
                            null, null, null);
                    cursor.moveToFirst();

                    // Retrieve the matched data
                    String msisdn = cursor.getString(cursor.getColumnIndex(projection[1]));
                    ContactInput contactInput = (ContactInput)auxView;
                    contactInput.setInputText(msisdn);

                }
                break;

        }

    }

    public JSONObject extractInputData() {
        Log.v(LOG_TAG, "extractInputData() called");
        JSONArray outputJsonArray = null;
        JSONObject jsonObject = null;
        String extractedText = "";

        try {
            outputJsonArray = new JSONArray();
            for (int i = 0; i < inputArrayList.size(); i++) {

                AbstractInput input = inputArrayList.get(i);
                extractedText = input.sanitize(input.getInputText());
                if (!input.hasValidInput()) {
                    if (validationErrors == 0) {
                        input.requestFocus();
                    }
                    validationErrors++;
                }
                jsonObject = new JSONObject().accumulate(returnVars[i], extractedText);
                outputJsonArray.put(jsonObject);
            }
            return new JSONObject().accumulate("output", outputJsonArray);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException: " + e.getMessage());
        }

        return null;
    }


    public interface DetailFragmentListener {
        //
        public void onDetailFragmentInteraction(JSONObject formData);
    }

}
