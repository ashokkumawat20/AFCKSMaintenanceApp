package in.afckstechnologies.afcksmaintenanceapp.view;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import in.afckstechnologies.afcksmaintenanceapp.R;
import in.afckstechnologies.afcksmaintenanceapp.models.ABrandNameDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.AItemNameDAO;
import in.afckstechnologies.afcksmaintenanceapp.utils.AppStatus;
import in.afckstechnologies.afcksmaintenanceapp.utils.Config;
import in.afckstechnologies.afcksmaintenanceapp.utils.Constant;
import in.afckstechnologies.afcksmaintenanceapp.utils.SmsListener;
import in.afckstechnologies.afcksmaintenanceapp.utils.WebClient;


public class AccessoryEntryView extends DialogFragment {

    Button placeBtn;
    private EditText quantityEdtTxt;
    private EditText descEdtTxt;
    TextView quantityTxt;
    ImageView addItemName, addBrandName;
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String identificationResponse = "";
    String accessoryResponse = "";
    JSONObject jsonObj;
    Boolean status;
    String message;
    String user_id;
    boolean click = true;
    int count = 0;
    View registerView;

    String quantity = "";
    private Spinner displayItemName, displayBrandName;

    String desc = "";

    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj, jsonLeadObj1;
    String itemNameResponse = "", aBrandNameResponse = "", fStatusResponse = "", addResponse = "", fixedAssestsResponse = "";
    JSONArray jsonArray;
    ArrayList<AItemNameDAO> itemnamelist;
    ArrayList<ABrandNameDAO> brandnamelist;

    String itemNameId = "";
    String itemBrandId = "";
    public static SmsListener mListener;
    String qty="",itemName="",brandName="";

    //Edit lead
    HashMap<String, String> edit_accesItemName;
    HashMap<String, String> edit_accessBrand;
    //Keys
    Set keys_Name;
    Set keys_Brand;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_accessory_entry, null);

        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.CENTER | Gravity.CENTER);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);
        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();


        quantityEdtTxt = (EditText) registerView.findViewById(R.id.quantityEdtTxt);
        descEdtTxt = (EditText) registerView.findViewById(R.id.descEdtTxt);
        quantityTxt=(TextView) registerView.findViewById(R.id.quantityTxt);
        addItemName = (ImageView) registerView.findViewById(R.id.addItemName);
        addBrandName = (ImageView) registerView.findViewById(R.id.addBrandName);
        displayItemName = (Spinner) registerView.findViewById(R.id.displayItemName);
        displayBrandName = (Spinner) registerView.findViewById(R.id.displayBrandName);
        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);
        qty=preferences.getString("qty","");

        //
        if(!qty.equals(""))
        {
            quantityTxt.setText(preferences.getString("qty",""));
            quantityEdtTxt.setText(preferences.getString("qty",""));
        }

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

                quantity = quantityEdtTxt.getText().toString();
                desc = descEdtTxt.getText().toString();

                if (validate(itemNameId,itemBrandId,quantity)) {

                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        if (click) {
                            //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                            sendData();
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
        addItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(context).isOnline()) {
                    AccessoryItemNameEntryView accessoryItemNameEntryView = new AccessoryItemNameEntryView();
                    accessoryItemNameEntryView.show(getActivity().getSupportFragmentManager(), "accessoryItemNameEntryView");

                } else {

                    Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();

                }
            }
        });

        addBrandName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(context).isOnline()) {
                    AccessoryBrandNameEntryView accessoryBrandNameEntryView = new AccessoryBrandNameEntryView();
                    accessoryBrandNameEntryView.show(getActivity().getSupportFragmentManager(), "accessoryBrandNameEntryView");

                } else {

                    Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();

                }
            }
        });

        AccessoryItemNameEntryView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                Toast.makeText(context, messageText, Toast.LENGTH_SHORT).show();
                new initItemNameSpinner().execute();
            }
        });
        AccessoryBrandNameEntryView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                Toast.makeText(context, messageText, Toast.LENGTH_SHORT).show();
                new initBrandNameSpinner().execute();

            }
        });

        new initItemNameSpinner().execute();
        new initBrandNameSpinner().execute();
        return registerView;
    }


    public boolean validate(String itemNameId,String itemBrandId,String quantity) {
        boolean isValidate = false;
        if (itemNameId.equals("0")) {
            Toast.makeText(getActivity(), "Please select item name .", Toast.LENGTH_LONG).show();
        }
        else if (itemBrandId.equals("0")) {
            Toast.makeText(getActivity(), "Please select brand name .", Toast.LENGTH_LONG).show();
        }
        else if (quantity.trim().equals("")) {
            Toast.makeText(getActivity(), "Please enter quantity.", Toast.LENGTH_LONG).show();
        } else {
            isValidate = true;
        }

        return isValidate;
    }


    public void sendData() {

        jsonObj = new JSONObject() {
            {
                try {

                    put("qty", quantity);
                    put("fixed_assets_id", preferences.getString("fm_id", ""));
                    put("accessory_id", itemNameId);
                    put("brand_id", itemBrandId);
                    put("description", desc);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();

                Log.i("loginResponse", "jsonObj" + jsonObj);
                identificationResponse = serviceAccess.SendHttpPost(Config.URL_ADDMOREACCESSORYDETAILS, jsonObj);
                Log.i("identificationResponse", "identificationResponse" + identificationResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (identificationResponse.compareTo("") == 0) {
                                    Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(identificationResponse);
                                        status = jObject.getBoolean("status");
                                        message = jObject.getString("message");


                                        if (status) {
                                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                            dismiss();
                                            prefEditor.putString("qty","");
                                            prefEditor.commit();
                                            update();

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

    //
    private class initItemNameSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
          //  mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
           // mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
           // mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
           // mProgressDialog.show();
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
            itemNameResponse = serviceAccess.SendHttpPost(Config.URL_GETALLAITEMNAME, jsonLeadObj);
            Log.i("resp", "leadListResponse" + itemNameResponse);

            if (itemNameResponse.compareTo("") != 0) {
                if (isJSONValid(itemNameResponse)) {

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                itemnamelist = new ArrayList<>();
                                itemnamelist.add(new AItemNameDAO("0", "Select item name"));
                                JSONArray LeadSourceJsonObj = new JSONArray(itemNameResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    itemnamelist.add(new AItemNameDAO(json_data.getString("id"), json_data.getString("accessory_name")));

                                }
                                edit_accesItemName = new HashMap<String, String>();
                                edit_accesItemName.put(preferences.getString("accessory_id",""),preferences.getString("accessory_name",""));
                                keys_Name = edit_accesItemName.keySet();
                                jsonArray = new JSONArray(itemNameResponse);

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
                            Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (itemNameResponse.compareTo("") != 0) {

                String key_name = "";
                String value_name = "";
                if (!keys_Name.isEmpty()) {
                    for (Iterator i = keys_Name.iterator(); i.hasNext(); ) {
                        key_name = (String) i.next();
                        value_name = (String) edit_accesItemName.get(key_name);
                        Log.d("keys ", "" + key_name + " = " + value_name);
                    }

                }


                // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<AItemNameDAO> adapter = new ArrayAdapter<AItemNameDAO>(context, android.R.layout.simple_spinner_dropdown_item, itemnamelist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                displayItemName.setAdapter(adapter);
                selectSpinnerItemByValue(displayItemName, value_name);
                displayItemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        AItemNameDAO LeadSource = (AItemNameDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getItemName(), Toast.LENGTH_SHORT).show();
                        itemNameId = LeadSource.getId();

                        if(!itemNameId.equals("0") &&!itemBrandId.equals("0")) {
                            new getAccessoryDetailsList().execute();
                        }


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
    private class initBrandNameSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
           // mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
           // mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
          //  mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
           // mProgressDialog.show();
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
            aBrandNameResponse = serviceAccess.SendHttpPost(Config.URL_GETALLABRANDNAME, jsonLeadObj);
            Log.i("resp", "aBrandNameResponse" + aBrandNameResponse);

            if (aBrandNameResponse.compareTo("") != 0) {
                if (isJSONValid(aBrandNameResponse)) {

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                brandnamelist = new ArrayList<>();
                                brandnamelist.add(new ABrandNameDAO("0", "Select Brand name"));
                                JSONArray LeadSourceJsonObj = new JSONArray(aBrandNameResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    brandnamelist.add(new ABrandNameDAO(json_data.getString("id"), json_data.getString("brand_name")));

                                }
                                edit_accessBrand = new HashMap<String, String>();
                                edit_accessBrand.put(preferences.getString("brand_id",""),preferences.getString("brand_name",""));
                                keys_Brand = edit_accessBrand.keySet();
                                jsonArray = new JSONArray(aBrandNameResponse);

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
                            Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (itemNameResponse.compareTo("") != 0) {
                String key_name = "";
                String value_name = "";
                if (!keys_Brand.isEmpty()) {
                    for (Iterator i = keys_Brand.iterator(); i.hasNext(); ) {
                        key_name = (String) i.next();
                        value_name = (String) edit_accessBrand.get(key_name);
                        Log.d("keys ", "" + key_name + " = " + value_name);
                    }

                }
                // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<ABrandNameDAO> adapter = new ArrayAdapter<ABrandNameDAO>(context, android.R.layout.simple_spinner_dropdown_item, brandnamelist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                displayBrandName.setAdapter(adapter);
                selectSpinnerItemByValue(displayBrandName, value_name);
                displayBrandName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        ABrandNameDAO LeadSource = (ABrandNameDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getItemName(), Toast.LENGTH_SHORT).show();
                        itemBrandId = LeadSource.getId();
                        if(!itemBrandId.equals("0") && !itemNameId.equals("0")) {
                            new getAccessoryDetailsList().execute();
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
               // mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
               // mProgressDialog.dismiss();
            }
        }
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
                            prefEditor.putString("qty","");
                            prefEditor.putString("accessory_id","");
                            prefEditor.putString("accessory_name","");
                            prefEditor.putString("brand_id","");
                            prefEditor.putString("brand_name","");
                            prefEditor.commit();
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

       // mListener.messageReceived(message);

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
    //
    private class getAccessoryDetailsList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
          //  mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
          //  mProgressDialog.setTitle("Please Wait...");
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
                        put("fixed_assets_id", preferences.getString("fm_id", ""));
                        put("accessory_id", itemNameId);
                        put("brand_id", itemBrandId);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            Log.i("json", "json" + jsonLeadObj);
            accessoryResponse = serviceAccess.SendHttpPost(Config.URL_GETACCESSORYDETAILS, jsonLeadObj);
            Log.i("resp", "studentfeesListResponse" + accessoryResponse);
            if (accessoryResponse.compareTo("") != 0) {
                if (isJSONValid(accessoryResponse)) {

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                JSONArray leadJsonObj = new JSONArray(accessoryResponse);

                                for (int i = 0; i < leadJsonObj.length(); i++) {
                                    JSONObject json_data = leadJsonObj.getJSONObject(i);
                                    descEdtTxt.setText(json_data.getString("description"));
                                    quantityTxt.setText(json_data.getString("qty"));
                                }



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
                            //Toast.makeText(getActivity(), "Please check your webservice", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(getActivity(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (true) {

               // mProgressDialog.dismiss();
               // dismiss();
            } else {
                // Close the progressdialog
               // Toast.makeText(getActivity(), "Not Available Attendance !", Toast.LENGTH_LONG).show();
             //   mProgressDialog.dismiss();

               // dismiss();
            }
        }
    }
    private void selectSpinnerItemByValue(Spinner spinner, String value) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

}