package in.afckstechnologies.afcksmaintenanceapp.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.afckstechnologies.afcksmaintenanceapp.R;
import in.afckstechnologies.afcksmaintenanceapp.utils.AppStatus;
import in.afckstechnologies.afcksmaintenanceapp.utils.Config;
import in.afckstechnologies.afcksmaintenanceapp.utils.Constant;
import in.afckstechnologies.afcksmaintenanceapp.utils.SmsListener;
import in.afckstechnologies.afcksmaintenanceapp.utils.WebClient;


public class AddSalesFixedAssetsView extends DialogFragment {

    Button placeBtn;
    private EditText quntityEdtTxt;
    private EditText descEdtTxt;

    private Spinner spnrUserType;

    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    DatePickerDialog dp = null;
    String addSaleResponse = "";
    JSONObject jsonObj;
    String device_id;
    Boolean status;
    String message;
    String user_id;
    boolean click = true;
    int count = 0;

    //8-3-2017

    View registerView;
    int pos;
    int pos1;

    String quntity = "";
    String desc = "";
    String type = "";
    public static SmsListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_addsales_fixedassests, null);

        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.CENTER | Gravity.CENTER);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);

        quntityEdtTxt = (EditText) registerView.findViewById(R.id.quntityEdtTxt);
        descEdtTxt = (EditText) registerView.findViewById(R.id.descEdtTxt);

        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();


        // device_id = getDeviceId();
        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);
//8-3-2017


        //

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        dataAdapter.add("Choose Remarks");
        dataAdapter.add("Purchase");
        dataAdapter.add("Add");
        dataAdapter.add("Sale");
        dataAdapter.add("Scrap");
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spnrUserType = (Spinner) registerView.findViewById(R.id.spnrType);
        spnrUserType.setBackgroundColor(Color.parseColor("#234e5e"));
        spnrUserType.setAdapter(dataAdapter);


        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //Hide your keyboard here!!!
                    Toast.makeText(getActivity(), "PLease enter your information to get us connected with you.", Toast.LENGTH_LONG).show();
                    return true; // pretend we've processed it
                } else
                    return false; // pass on to be processed as normal
            }
        });

        placeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                quntity= quntityEdtTxt.getText().toString();
                desc= descEdtTxt.getText().toString();
                type= spnrUserType.getSelectedItem().toString();


                if (validate(quntity, type)) {

                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        if (click) {
                            //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                            sendData(quntity, type, desc);
                            click = false;
                        } else {
                            Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }


                } else {

                }

            }
        });
        return registerView;
    }





    public boolean validate(String quntity, String type) {
        boolean isValidate = false;
        if (quntity.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please Enter Quntity", Toast.LENGTH_LONG).show();
        } else if (type.equals("Choose Remaks")) {
            Toast.makeText(getActivity(), "Please select option", Toast.LENGTH_LONG).show();
            isValidate = false;

        }    else {
            isValidate = true;
        }
        return isValidate;
    }



    public void sendData(final String quntity, final String type, final String desc) {
        jsonObj = new JSONObject() {
            {
                try {
                    put("Remark", type);
                    put("ItemDesc", desc);
                    put("Qty",quntity);
                    put("IdentificationID",preferences.getString("identificationNameID",""));
                    put("LocationID", preferences.getString("locationNameID",""));
                    put("StatusID", preferences.getString("statusNameID",""));
                    put("UserID", preferences.getString("maintenance_user_id",""));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();
                Log.i("addSaleResponse", "jsonObj" + jsonObj);
                addSaleResponse = serviceAccess.SendHttpPost(Config.URL_ADDMOREASSESTSDETAILS, jsonObj);

                Log.i("addSaleResponse", "addSaleResponse" + addSaleResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (addSaleResponse.compareTo("") == 0) {
                                    Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(addSaleResponse);
                                        status = jObject.getBoolean("status");
                                        message = jObject.getString("message");
                                        if (status) {
                                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                            update();
                                            dismiss();
                                        } else {
                                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                            dismiss();
                                        }
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                };

                new Thread(runnable).start();
            }
        });
        objectThread.start();
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //This is the filter
                    if (event.getAction() != KeyEvent.ACTION_DOWN) {
                        // Toast.makeText(getActivity(), "Your information is valuable for us and won't be misused.", Toast.LENGTH_SHORT).show();
                        count++;
                        if (count >= 1) {
                         dismiss();
                        }
                        return true;
                    } else {
                        //Hide your keyboard here!!!!!!
                        return true; // pretend we've processed it
                    }
                } else
                    return false; // pass on to be processed as normal
            }
        });
    }

    private void update() {

        mListener.messageReceived(message);

    }




    protected boolean isJSONValid(String callReoprtResponse2) {
        // TODO Auto-generated method stub
        try {
            new JSONObject(callReoprtResponse2);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(callReoprtResponse2);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

}