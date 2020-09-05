package in.afckstechnologies.afcksmaintenanceapp.view;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.afckstechnologies.afcksmaintenanceapp.R;
import in.afckstechnologies.afcksmaintenanceapp.models.FALocationDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.FAStatusDAO;
import in.afckstechnologies.afcksmaintenanceapp.utils.AppStatus;
import in.afckstechnologies.afcksmaintenanceapp.utils.Config;
import in.afckstechnologies.afcksmaintenanceapp.utils.Constant;
import in.afckstechnologies.afcksmaintenanceapp.utils.SmsListener;
import in.afckstechnologies.afcksmaintenanceapp.utils.WebClient;


public class TransferFixedAssestsView extends DialogFragment {

    Button placeBtn;
    private EditText quntityEdtTxt;
    private EditText descEdtTxt;

    private Spinner spnrUserType;

    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    DatePickerDialog dp = null;
    String outTransferaddSaleResponse = "",fLocationResponse="",fStatusResponse="";
    JSONObject jsonObjC,jsonObjT;
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



    ProgressDialog mProgressDialog;

    JSONObject jsonCenterObj, jsonobject, jsonLeadObj;
    JSONArray jsonarray;
    String centerResponse = "";
    String phoneNumber = "", emailAddress = "";
    public static SmsListener mListener;
    ArrayList<FALocationDAO> flocationlist;
    ArrayList<FAStatusDAO> fstatuslist;
    JSONArray jsonArray;
    Spinner displayLocationName,displayStatusName;
    String locationNameId="",statusId="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_transfer_fixedassests, null);

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
        displayLocationName=(Spinner)registerView.findViewById(R.id.displayLocationName);
        displayStatusName=(Spinner)registerView.findViewById(R.id.displayStatusName);

        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();


        // device_id = getDeviceId();
        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);
        //
        new initFLocationSpinner().execute();

       new initFStatusSpinner().execute();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        dataAdapter.add("Choose Remarks");
        dataAdapter.add("Transfer");
        dataAdapter.add("On Damage");
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
        if (locationNameId.equals("0")) {
            Toast.makeText(getActivity(), "Please select Location", Toast.LENGTH_LONG).show();
        }
       else if (statusId.equals("0")) {
            Toast.makeText(getActivity(), "Please select Status", Toast.LENGTH_LONG).show();
        }
       else if (quntity.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please Enter Quntity", Toast.LENGTH_LONG).show();
        } else if (type.equals("Choose Remaks")) {
            Toast.makeText(getActivity(), "Please select option", Toast.LENGTH_LONG).show();
            isValidate = false;

        }  else {
            isValidate = true;
        }
        return isValidate;
    }



    public void sendData(final String quntity, final String type, final String desc) {

        jsonObjC = new JSONObject() {
            {
                try {
                    put("Remark", type);
                    put("ItemDesc", desc);
                    put("Qty","-"+quntity);
                    put("IdentificationID",preferences.getString("identificationNameID",""));
                    put("LocationID", preferences.getString("locationNameID",""));
                    put("StatusID", preferences.getString("statusNameID",""));
                    put("UserID", preferences.getString("maintenance_user_id",""));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        jsonObjT = new JSONObject() {
            {
                try {
                    put("Remark", type);
                    put("ItemDesc", desc);
                    put("Qty",quntity);
                    put("IdentificationID",preferences.getString("identificationNameID",""));
                    put("LocationID", locationNameId);
                    put("StatusID", statusId);
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

                Log.i("loginResponse", "jsonObj" + jsonObjC);
                Log.i("loginResponse", "jsonObj" + jsonObjT);
                outTransferaddSaleResponse = serviceAccess.SendHttpPost(Config.URL_ADDMOREASSESTSDETAILS, jsonObjC);
                outTransferaddSaleResponse = serviceAccess.SendHttpPost(Config.URL_ADDMOREASSESTSDETAILS, jsonObjT);
                Log.i("loginResponse", "loginResponse" + outTransferaddSaleResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (outTransferaddSaleResponse.compareTo("") == 0) {
                                    Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(outTransferaddSaleResponse);
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


    //
    private class initFLocationSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //   mProgressDialog = new ProgressDialog(FixedAssetsActivity.this);
            // Set progressdialog title
            //   mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            // mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //  mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            fLocationResponse = serviceAccess.SendHttpPost(Config.URL_GETALLFLOCATIONNAME, jsonLeadObj);
            Log.i("resp", "leadListResponse" + fLocationResponse);

            if (fLocationResponse.compareTo("") != 0) {
                if (isJSONValid(fLocationResponse)) {

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                flocationlist = new ArrayList<>();
                                flocationlist.add(new FALocationDAO("0", "Select location name"));
                                JSONArray LeadSourceJsonObj = new JSONArray(fLocationResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    flocationlist.add(new FALocationDAO(json_data.getString("id"), json_data.getString("location")));

                                }

                                jsonArray = new JSONArray(fLocationResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (fLocationResponse.compareTo("") != 0) {

                // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<FALocationDAO> adapter = new ArrayAdapter<FALocationDAO>(context, android.R.layout.simple_spinner_dropdown_item, flocationlist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                displayLocationName.setAdapter(adapter);
                displayLocationName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        FALocationDAO LeadSource = (FALocationDAO) parent.getSelectedItem();
                      //  Toast.makeText(context, "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getLocation_name(), Toast.LENGTH_SHORT).show();
                        locationNameId = LeadSource.getId();
                      /*  if(locationNameId.equals(preferences.getString("locationNameID",""))) {

                            Toast.makeText(context, "Please choose another location ", Toast.LENGTH_SHORT).show();
                           // new initFLocationSpinner().execute();
                        }
                        else{


                        }*/

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
               // mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
            //    mProgressDialog.dismiss();
            }
        }
    }

    //
    private class initFStatusSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //   mProgressDialog = new ProgressDialog(FixedAssetsActivity.this);
            // Set progressdialog title
            //   mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            // mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //  mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            fStatusResponse = serviceAccess.SendHttpPost(Config.URL_GETALLFSTATUSNAME, jsonLeadObj);
            Log.i("resp", "leadListResponse" + fStatusResponse);

            if (fStatusResponse.compareTo("") != 0) {
                if (isJSONValid(fStatusResponse)) {

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                fstatuslist = new ArrayList<>();
                                fstatuslist.add(new FAStatusDAO("0", "Select status name"));
                                JSONArray LeadSourceJsonObj = new JSONArray(fStatusResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    fstatuslist.add(new FAStatusDAO(json_data.getString("id"), json_data.getString("status")));

                                }

                                jsonArray = new JSONArray(fStatusResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (fStatusResponse.compareTo("") != 0) {

                // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<FAStatusDAO> adapter = new ArrayAdapter<FAStatusDAO>(getActivity(), android.R.layout.simple_spinner_dropdown_item, fstatuslist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                displayStatusName.setAdapter(adapter);
                displayStatusName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        FAStatusDAO LeadSource = (FAStatusDAO) parent.getSelectedItem();
                        //  Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getStatus(), Toast.LENGTH_SHORT).show();
                        statusId = LeadSource.getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
              //  mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
               // mProgressDialog.dismiss();
            }
        }
    }
    //
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