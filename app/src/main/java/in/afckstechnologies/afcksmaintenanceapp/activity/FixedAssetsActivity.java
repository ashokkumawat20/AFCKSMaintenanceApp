package in.afckstechnologies.afcksmaintenanceapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import in.afckstechnologies.afcksmaintenanceapp.R;
import in.afckstechnologies.afcksmaintenanceapp.adapter.FixedAssestsListAdpter;
import in.afckstechnologies.afcksmaintenanceapp.jsonparser.JsonHelper;
import in.afckstechnologies.afcksmaintenanceapp.models.FALocationDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.FAStatusDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.FItemNameDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.FixedAssestsDAO;
import in.afckstechnologies.afcksmaintenanceapp.utils.AppStatus;
import in.afckstechnologies.afcksmaintenanceapp.utils.Config;
import in.afckstechnologies.afcksmaintenanceapp.utils.Constant;
import in.afckstechnologies.afcksmaintenanceapp.utils.SmsListener;
import in.afckstechnologies.afcksmaintenanceapp.utils.VersionChecker;
import in.afckstechnologies.afcksmaintenanceapp.utils.WebClient;
import in.afckstechnologies.afcksmaintenanceapp.view.AccessorySearchAntiKeysEntryView;
import in.afckstechnologies.afcksmaintenanceapp.view.AddSalesFixedAssetsView;
import in.afckstechnologies.afcksmaintenanceapp.view.IdentificationEntryView;
import in.afckstechnologies.afcksmaintenanceapp.view.IdentificationEntryViewUpdate;
import in.afckstechnologies.afcksmaintenanceapp.view.RepairOndamageFixedAssestsView;
import in.afckstechnologies.afcksmaintenanceapp.view.TransferFixedAssestsView;

public class FixedAssetsActivity extends AppCompatActivity {
    Spinner displayItemName,displayLocationName,displayStatusName;
    ImageView addItemName,addLocationName;
    String itemName="",locationName="";

    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj, jsonObj1;
    String itemNameResponse="",fLocationResponse="",fStatusResponse="",addResponse="",fixedAssestsResponse="";
    JSONArray jsonArray;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    ArrayList<FItemNameDAO> itemnamelist;
    ArrayList<FALocationDAO> flocationlist;
    ArrayList<FAStatusDAO> fstatuslist;

    String itemNameId="0",locationNameId="0",statusId="0";

    boolean status;
    String message = "";
    Button adddNewIdentification,viewAccess;
    LinearLayout identification;

    List<FixedAssestsDAO> data;
    FixedAssestsListAdpter fixedAssestsListAdpter;
    private RecyclerView mstudentList;

    public EditText search;
    String flag = "0";
    ImageView serach_hide, clear;
    TextView totalcount;
    LinearLayout totalview;


    String verifyMobileDeviceIdResponse = "";
    boolean statusv;
    String mobileDeviceId = "";
    JSONObject jsonObj;


    private String latestVersion = "", smspassResponse = "", getRoleusersResponse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_assets);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        mstudentList = (RecyclerView) findViewById(R.id.fixedAssestsList);
        displayItemName=(Spinner)findViewById(R.id.displayItemName);
        displayLocationName=(Spinner)findViewById(R.id.displayLocationName);
        addItemName=(ImageView)findViewById(R.id.addItemName);
        addLocationName=(ImageView)findViewById(R.id.addLocationName);
        displayStatusName=(Spinner)findViewById(R.id.displayStatusName);
        adddNewIdentification=(Button)findViewById(R.id.adddNewIdentification);
        viewAccess=(Button)findViewById(R.id.viewAccess);
        identification=(LinearLayout)findViewById(R.id.identification);
        totalview=(LinearLayout)findViewById(R.id.totalview);
        search = (EditText) findViewById(R.id.search);
        serach_hide = (ImageView) findViewById(R.id.serach_hide);
        clear = (ImageView) findViewById(R.id.clear);
        totalcount=(TextView)findViewById(R.id.totalcount);
        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            verifyMobileDeviceId();
            new initItemNameSpinner().execute();

            new initFLocationSpinner().execute();

            new initFStatusSpinner().execute();
        } else {

            Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
        }

        addItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FixedAssetsActivity.this);
                final EditText input = new EditText(FixedAssetsActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                // alertDialog.setIcon(R.drawable.msg_img);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                itemName = input.getText().toString().trim();
                                if (!itemName.equals("")) {
                                    dialog.cancel();
                                    new addItemName().execute();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Please enter new item name", Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.setTitle("Adding New Item Name");
                alertDialog.show();

            }
        });
        addLocationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FixedAssetsActivity.this);
                final EditText input = new EditText(FixedAssetsActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                // alertDialog.setIcon(R.drawable.msg_img);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                locationName = input.getText().toString().trim();
                                if (!locationName.equals("")) {

                                    dialog.cancel();
                                    new addLocationName().execute();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please enter new location name", Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.setTitle("Adding New Location Name");
                alertDialog.show();
            }
        });

        adddNewIdentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IdentificationEntryView identificationEntryView = new IdentificationEntryView();
                identificationEntryView.show(getSupportFragmentManager(), "identificationEntryView");
            }
        });


        IdentificationEntryView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                new getFixedAssestsList().execute();
            }
        });
        AddSalesFixedAssetsView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                new getFixedAssestsList().execute();
            }
        });
        TransferFixedAssestsView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                new getFixedAssestsList().execute();
            }
        });

        RepairOndamageFixedAssestsView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                new getFixedAssestsList().execute();
            }
        });

        AccessorySearchAntiKeysEntryView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                new getFixedAssestsList().execute();
            }
        });
        IdentificationEntryViewUpdate.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                new getFixedAssestsList().execute();
            }
        });
        serach_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                clear.setVisibility(View.VISIBLE);
                serach_hide.setVisibility(View.GONE);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
                serach_hide.setVisibility(View.VISIBLE);
                clear.setVisibility(View.GONE);

            }
        });
        addTextListener();

        viewAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(FixedAssetsActivity.this,ViewAccessoriesActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Called when the activity is about to become visible.
     */
    @Override
    protected void onStart() {
        super.onStart();

        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            getUserRoles();


        }


    }
    //
    public void addTextListener() {

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                final List<FixedAssestsDAO> filteredList = new ArrayList<FixedAssestsDAO>();
                if (data != null) {
                    if (data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {

                            String identification1 = data.get(i).getIdentification().toLowerCase();
                            String identification2  = data.get(i).getIdentification2().toLowerCase();
                            String identification3  = data.get(i).getIdentification3().toLowerCase();

                            if (identification1.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (identification2.contains(query)) {

                                filteredList.add(data.get(i));
                            }
                            else if (identification3.contains(query)) {

                                filteredList.add(data.get(i));
                            }
                        }
                    }
                }

                mstudentList.setLayoutManager(new LinearLayoutManager(FixedAssetsActivity.this));
                fixedAssestsListAdpter = new FixedAssestsListAdpter(FixedAssetsActivity.this, filteredList);
                mstudentList.setAdapter(fixedAssestsListAdpter);
                fixedAssestsListAdpter.notifyDataSetChanged();  // data set changed
            }
        });
    }
    //
    private class initItemNameSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(FixedAssetsActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
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
            itemNameResponse = serviceAccess.SendHttpPost(Config.URL_GETALLFITEMNAME, jsonLeadObj);
            Log.i("resp", "leadListResponse" + itemNameResponse);

            if (itemNameResponse.compareTo("") != 0) {
                if (isJSONValid(itemNameResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                itemnamelist = new ArrayList<>();
                                itemnamelist.add(new FItemNameDAO("0", "Select item name"));
                                JSONArray LeadSourceJsonObj = new JSONArray(itemNameResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    itemnamelist.add(new FItemNameDAO(json_data.getString("id"), json_data.getString("ItemName")));

                                }

                                jsonArray = new JSONArray(itemNameResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (itemNameResponse.compareTo("") != 0) {

                // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<FItemNameDAO> adapter = new ArrayAdapter<FItemNameDAO>(FixedAssetsActivity.this, android.R.layout.simple_spinner_dropdown_item, itemnamelist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                displayItemName.setAdapter(adapter);
                displayItemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        FItemNameDAO LeadSource = (FItemNameDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getItemName(), Toast.LENGTH_SHORT).show();
                        itemNameId = LeadSource.getId();

                        if(!itemNameId.equals("0"))
                        {
                            identification.setVisibility(View.VISIBLE);
                            prefEditor.putString("itemNameId",itemNameId);
                            prefEditor.putString("itemName",LeadSource.getItemName());
                            prefEditor.commit();
                        }

                        if(!itemNameId.equals("0"))
                        {
                            //  Toast.makeText(getApplicationContext(),itemNameId+" "+locationNameId+" "+statusId, Toast.LENGTH_SHORT).show();
                            new getFixedAssestsList().execute();
                        }
                        if(statusId.equals("0") || itemNameId.equals("0")||locationNameId.equals("0"))
                        {
                            identification.setVisibility(View.GONE);

                        }
                        else
                        {
                            identification.setVisibility(View.VISIBLE);
                        }
                        if(statusId.equals("0") && itemNameId.equals("0")&& locationNameId.equals("0"))
                        {

                            if (fixedAssestsResponse.compareTo("") != 0) {
                                data.clear(); // this list which you hava passed in Adapter for your listview
                                fixedAssestsListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                            }
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
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

                    runOnUiThread(new Runnable() {

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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
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
                ArrayAdapter<FALocationDAO> adapter = new ArrayAdapter<FALocationDAO>(FixedAssetsActivity.this, android.R.layout.simple_spinner_dropdown_item, flocationlist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                displayLocationName.setAdapter(adapter);
                displayLocationName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        FALocationDAO LeadSource = (FALocationDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getLocation_name(), Toast.LENGTH_SHORT).show();
                        locationNameId = LeadSource.getId();
                        prefEditor.putString("locationName",LeadSource.getLocation_name());
                        prefEditor.putString("locationNameID",LeadSource.getId());
                        prefEditor.commit();
                        if(!locationNameId.equals("0"))
                        {
                            // Toast.makeText(getApplicationContext(),itemNameId+" "+locationNameId+" "+statusId, Toast.LENGTH_SHORT).show();
                            new getFixedAssestsList().execute();
                        }
                        if(statusId.equals("0") || itemNameId.equals("0")||locationNameId.equals("0"))
                        {
                            identification.setVisibility(View.GONE);

                        }
                        else
                        {
                            identification.setVisibility(View.VISIBLE);
                        }
                        if(statusId.equals("0") && itemNameId.equals("0")&& locationNameId.equals("0"))
                        {

                            if (fixedAssestsResponse.compareTo("") != 0) {
                                data.clear(); // this list which you hava passed in Adapter for your listview
                                fixedAssestsListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
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

                    runOnUiThread(new Runnable() {

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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
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
                ArrayAdapter<FAStatusDAO> adapter = new ArrayAdapter<FAStatusDAO>(FixedAssetsActivity.this, android.R.layout.simple_spinner_dropdown_item, fstatuslist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                displayStatusName.setAdapter(adapter);
                displayStatusName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        FAStatusDAO LeadSource = (FAStatusDAO) parent.getSelectedItem();
                        //   Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getStatus(), Toast.LENGTH_SHORT).show();
                        statusId = LeadSource.getId();
                        prefEditor.putString("statusName",LeadSource.getStatus());
                        prefEditor.putString("statusNameID",LeadSource.getId());
                        prefEditor.commit();
                        if(!statusId.equals("0"))
                        {
                            // Toast.makeText(getApplicationContext(),itemNameId+" "+locationNameId+" "+statusId, Toast.LENGTH_SHORT).show();
                            new getFixedAssestsList().execute();
                        }
                        if(statusId.equals("0") || itemNameId.equals("0")||locationNameId.equals("0"))
                        {
                            identification.setVisibility(View.GONE);

                        }
                        else
                        {
                            identification.setVisibility(View.VISIBLE);
                        }

                        if(statusId.equals("0") && itemNameId.equals("0")&& locationNameId.equals("0"))
                        {

                            if (fixedAssestsResponse.compareTo("") != 0) {
                                data.clear(); // this list which you hava passed in Adapter for your listview
                                fixedAssestsListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                            }
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }
    //

    //
    private class getFixedAssestsList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(FixedAssetsActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("LocationID", locationNameId);
                        put("ItemNameID", itemNameId);
                        put("StatusID", statusId);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            fixedAssestsResponse = serviceAccess.SendHttpPost(Config.URL_GETALLFIXEDASSESTS, jsonLeadObj);
            Log.i("resp", "batchesListResponse" + fixedAssestsResponse);
            if (fixedAssestsResponse.compareTo("") != 0) {
                if (isJSONValid(fixedAssestsResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                data.clear();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseFixedAssetsList(fixedAssestsResponse);
                                jsonArray = new JSONArray(fixedAssestsResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (data.size()>0) {
                totalview.setVisibility(View.VISIBLE);
                totalcount.setText(""+data.size());
                fixedAssestsListAdpter = new FixedAssestsListAdpter(FixedAssetsActivity.this, data);
                mstudentList.setAdapter(fixedAssestsListAdpter);
                mstudentList.setLayoutManager(new LinearLayoutManager(FixedAssetsActivity.this));
                fixedAssestsListAdpter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog

                totalcount.setText("");
                totalview.setVisibility(View.GONE);
                mProgressDialog.dismiss();
                data.clear(); // this list which you hava passed in Adapter for your listview
                fixedAssestsListAdpter = new FixedAssestsListAdpter(FixedAssetsActivity.this, data);
                mstudentList.setAdapter(fixedAssestsListAdpter);
                mstudentList.setLayoutManager(new LinearLayoutManager(FixedAssetsActivity.this));
                fixedAssestsListAdpter.notifyDataSetChanged();

            }
        }
    }
    private class addItemName extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(FixedAssetsActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {


            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final String datezero = df.format(c.getTime());
            jsonLeadObj = new JSONObject() {
                {
                    try {

                        put("itemname", itemName);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            addResponse = serviceAccess.SendHttpPost(Config.URL_ADDFITEMNAME, jsonLeadObj);
            Log.i("resp", "leadListResponse" + addResponse);


            if (addResponse.compareTo("") != 0) {
                if (isJSONValid(addResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(addResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(addResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(FixedAssetsActivity.this, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(FixedAssetsActivity.this, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(FixedAssetsActivity.this, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
                new initItemNameSpinner().execute();
            } else {
                Toast.makeText(FixedAssetsActivity.this, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    //
    private class addLocationName extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(FixedAssetsActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {


            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final String datezero = df.format(c.getTime());
            jsonLeadObj = new JSONObject() {
                {
                    try {

                        put("locationname", locationName);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            addResponse = serviceAccess.SendHttpPost(Config.URL_ADDFLOCATIONNAME, jsonLeadObj);
            Log.i("resp", "leadListResponse" + addResponse);


            if (addResponse.compareTo("") != 0) {
                if (isJSONValid(addResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(addResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(addResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(FixedAssetsActivity.this, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(FixedAssetsActivity.this, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(FixedAssetsActivity.this, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
                new initFLocationSpinner().execute();
            } else {
                Toast.makeText(FixedAssetsActivity.this, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
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
public void verifyMobileDeviceId() {


    jsonObj = new JSONObject() {
        {
            try {
                put("pDeviceID", preferences.getString("maintenance_mobile_deviceid", ""));
                put("role_id", "8");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Thread objectThread = new Thread(new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            WebClient serviceAccess = new WebClient();
            verifyMobileDeviceIdResponse = serviceAccess.SendHttpPost(Config.URL_GETAVAILABLEMOBILEDEVICES, jsonObj);
            Log.i("loginResponse", "verifyMobileDeviceIdResponse" + verifyMobileDeviceIdResponse);
            final Handler handler = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() { // This thread runs in the UI
                        @Override
                        public void run() {
                            if (verifyMobileDeviceIdResponse.compareTo("") == 0) {

                            } else {

                                try {
                                    JSONObject jObject = new JSONObject(verifyMobileDeviceIdResponse);
                                    statusv = jObject.getBoolean("status");

                                    if (statusv) {

                                    }

                                    else {
                                        finish();
                                        prefEditor.putString("maintenance_user_id", "");
                                        prefEditor.commit();
                                        Intent i = new Intent(FixedAssetsActivity.this, SplashScreenActivity.class);
                                        startActivity(i);
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

    public void getUserRoles() {


        jsonObj1 = new JSONObject() {
            {
                try {
                    put("role_id", "8");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();
                getRoleusersResponse = serviceAccess.SendHttpPost(Config.URL_GETAVAILABLEROLES, jsonObj1);
                Log.i("getRoleusersResponse", getRoleusersResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (getRoleusersResponse.compareTo("") == 0) {

                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(getRoleusersResponse);
                                        status = jObject.getBoolean("status");

                                        if (status) {
                                            forceUpdate();
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

    public void forceUpdate() {
        //  int playStoreVersionCode = FirebaseRemoteConfig.getInstance().getString("android_latest_version_code");
        VersionChecker versionChecker = new VersionChecker();
        try {
            latestVersion = versionChecker.execute().get();
            /*if (latestVersion.length() > 0) {
                latestVersion = latestVersion.substring(50, 58);
                latestVersion = latestVersion.trim();
            }*/


            Log.d("versoncode", "" + latestVersion);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //  String currentVersion = packageInfo.versionName;
        String currentVersion = packageInfo.versionName;

        new ForceUpdateAsync(currentVersion, FixedAssetsActivity.this).execute();

    }

    public class ForceUpdateAsync extends AsyncTask<String, String, JSONObject> {


        private String currentVersion;
        private Context context;

        public ForceUpdateAsync(String currentVersion, Context context) {
            this.currentVersion = currentVersion;
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {


            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (latestVersion != null) {
                if (!latestVersion.equals("")) {
                    if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                        // Toast.makeText(context,"update is available.",Toast.LENGTH_LONG).show();

                        if (!((Activity) context).isFinishing()) {
                            showForceUpdateDialog();
                        }


                    }
                } else {
                    if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                        // AppUpdater appUpdater = new AppUpdater((Activity) context);
                        //  appUpdater.start();
                    } else {

                        Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }

                }
            }
            super.onPostExecute(jsonObject);
        }

        public void showForceUpdateDialog() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme));

            alertDialogBuilder.setTitle(context.getString(R.string.youAreNotUpdatedTitle));
            alertDialogBuilder.setMessage(context.getString(R.string.youAreNotUpdatedMessage) + " " + latestVersion + context.getString(R.string.youAreNotUpdatedMessage1));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
                    dialog.cancel();
                }
            });
            alertDialogBuilder.show();
        }
    }
    //
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}

