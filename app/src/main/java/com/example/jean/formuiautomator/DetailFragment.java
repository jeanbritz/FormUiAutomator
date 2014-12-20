package com.example.jean.formuiautomator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.jean.formuiautomator.inputs.BooleanInput;
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

    /**
     *  Debug tag for logging debug output to LogCat
     */
    private final String LOG_TAG = getClass().getSimpleName();


    private static final String ARG_ACTION_BAR_TITLE = "formTitle";
    private static final String ARG_FORM_DATA_JSON = "formJson";

    private String mActionBarTitle;
    private String mFormDataJson;

    private DetailFragmentListener mCallback;

    private final int PICK_CONTACT_REQUEST = 100;

    String returnVars[];
    int validationErrors = 0;

    private View auxView;
    List<RelativeLayout> customInputView = new ArrayList<RelativeLayout>();
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mTitle Title set in ActionBar.
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

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mActionBarTitle = getArguments().getString(ARG_ACTION_BAR_TITLE);
            mFormDataJson = getArguments().getString(ARG_FORM_DATA_JSON);
        }
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

        TextInput textInput;
        ContactInput contactInput;
        BooleanInput booleanInput;
        DateInput dateInput;

        try {
            inputsJsonArray = new JSONArray(mFormDataJson);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            returnVars = new String[inputsJsonArray.length()];
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
                    booleanInput = new BooleanInput(context, inputLabelText);

                    linearLayout.addView(booleanInput, params);
                    customInputView.add(booleanInput);
                } else if (inputMaskType.equalsIgnoreCase(AppConstants._CONTACT_INPUT)) {
                    contactInput = new ContactInput(context, inputLabelText);
                    contactInput.setOnClickListener(new ContactInput.ContactInputListener() {
                        @Override
                        public void onChooseContact(View view) {
                            auxView = view;
                            Intent pickContactIntent = new Intent(Intent.ACTION_PICK,
                                    Uri.parse("content://contacts"));
                            pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                            startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
                        }
                    });
                    linearLayout.addView(contactInput, params);
                    customInputView.add(contactInput);
                } else if (inputMaskType.equalsIgnoreCase(AppConstants._DATE_INPUT)) {
                    dateInput = new DateInput(context, inputLabelText);
                    dateInput.setOnClickListener(new DateInput.DateInputListener() {
                        @Override
                        public void onChooseDate(View view) {
                            auxView = view;

                        }
                    });
                    linearLayout.addView(dateInput, params);
                    customInputView.add(dateInput);
                } else if (inputMaskType.equalsIgnoreCase(AppConstants._PASSWORD_INPUT)) {
                    textInput = new TextInput(context, inputLabelText,
                            AppConstants._PASSWORD_INPUT);
                    linearLayout.addView(textInput, params);
                    customInputView.add(textInput);
                } else if (inputMaskType.equalsIgnoreCase(AppConstants._NUMBER_INPUT)) {
                    textInput = new TextInput(context, inputLabelText, AppConstants._NUMBER_INPUT);
                    linearLayout.addView(textInput, params);
                    customInputView.add(textInput);
                } else {
                    textInput = new TextInput(context, inputLabelText);
                    linearLayout.addView(textInput, params);
                    customInputView.add(textInput);
                }
                Log.d(LOG_TAG, "Adding " + inputMaskType + " input labeled : [ " + inputLabelText + " ]");

        }

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
                    + " must implement OnFragmentInteractionListener");
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

                    // Retrieve that matched data
                    String displayName = cursor.getString(cursor.getColumnIndex(projection[0]));
                    String msisdn = cursor.getString(cursor.getColumnIndex(projection[1]));
                    ContactInput contactInput = (ContactInput)auxView;
                    contactInput.setInputText(msisdn);
                    contactInput.sanitize();
                }
                break;

        }

    }

    public JSONObject retrieveInputData () {
        Log.v(LOG_TAG, "retrieveInputData() called");
        JSONArray inputJsonArray = null;
        JSONObject temp = null;
        String val = "";
        EditText editText = null;

        try {
            inputJsonArray = new JSONArray();
            for (int i = 0; i < customInputView.size(); i++) {

                if(customInputView.get(i)  instanceof TextInput) {
                    TextInput tempInput = (TextInput)customInputView.get(i);
                    editText = tempInput.getInput();
                    if(!tempInput.hasValidInput()) {
                        validationErrors++;
                    }
                }
                else if(customInputView.get(i) instanceof ContactInput) {
                    ContactInput tempInput = (ContactInput)customInputView.get(i);
                    if(!tempInput.hasValidInput()) {
                        validationErrors++;
                    }
                    editText = tempInput.getInput();
                }
                else if(customInputView.get(i) instanceof  BooleanInput) {
                    BooleanInput tempInput = ((BooleanInput)customInputView.get(i));
                    editText = new EditText(getActivity());
                    editText.setText(tempInput.getInput()+"");
                }
                else if(customInputView.get(i) instanceof  DateInput) {
                    DateInput tempInput = (DateInput)customInputView.get(i);
                    editText = tempInput.getInput();
                }


                temp = new JSONObject().accumulate(returnVars[i], editText.getText().toString());
                inputJsonArray.put(temp);

            }
            return new JSONObject().accumulate("output", inputJsonArray);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException: " + e.getMessage());
        }

        return null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface DetailFragmentListener {
        //
        public void onDetailFragmentInteraction(JSONObject formData);
    }

}
