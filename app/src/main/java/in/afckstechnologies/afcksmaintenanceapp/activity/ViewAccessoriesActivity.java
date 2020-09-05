package in.afckstechnologies.afcksmaintenanceapp.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.afckstechnologies.afcksmaintenanceapp.R;
import in.afckstechnologies.afcksmaintenanceapp.adapter.ViewAccessoriesListAdpter;
import in.afckstechnologies.afcksmaintenanceapp.jsonparser.JsonHelper;
import in.afckstechnologies.afcksmaintenanceapp.models.ABrandNameDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.AItemNameDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.FALocationDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.FAStatusDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.FItemNameDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.ViewAccessoriesListDAO;
import in.afckstechnologies.afcksmaintenanceapp.utils.Config;
import in.afckstechnologies.afcksmaintenanceapp.utils.WebClient;

public class ViewAccessoriesActivity extends AppCompatActivity {
    private JSONObject jsonLeadObj, jsonLeadObj1;
    JSONArray jsonArray;
    ProgressDialog mProgressDialog;
    ArrayList<AItemNameDAO> accessoriesItemnamelist;
    ArrayList<ABrandNameDAO> brandnamelist;
    String accessoriesItemNameResponse = "", aBrandNameResponse = "",itemNameResponse="",fLocationResponse="",fStatusResponse="",viewAccessoriesAssestsResponse="";
    String accessoriesItemNameId = "0";
    String itemBrandId = "0";
    private Spinner accessoriesDisplayItemName, displayBrandName, displayItemName,displayLocationName,displayStatusName;
    String itemNameId="0",locationNameId="0",statusId="0";
    ArrayList<FItemNameDAO> itemnamelist;
    ArrayList<FALocationDAO> flocationlist;
    ArrayList<FAStatusDAO> fstatuslist;
    List<ViewAccessoriesListDAO> data;
    ViewAccessoriesListAdpter viewAccessoriesListAdpter;
    private RecyclerView mstudentList;
    TextView totalcount;
    LinearLayout totalview;
    public EditText search;
    ImageView serach_hide, clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accessories);
        accessoriesDisplayItemName = (Spinner) findViewById(R.id.displayViewAccessoriesItemName);
        displayBrandName = (Spinner) findViewById(R.id.displayBrandName);
        displayItemName=(Spinner)findViewById(R.id.displayItemName);
        displayLocationName=(Spinner)findViewById(R.id.displayLocationName);
        displayStatusName=(Spinner)findViewById(R.id.displayStatusName);
        mstudentList = (RecyclerView) findViewById(R.id.viewAccessoriesList);
        totalview=(LinearLayout)findViewById(R.id.totalview);
        totalcount=(TextView)findViewById(R.id.totalcount);
        search = (EditText) findViewById(R.id.search);
        serach_hide = (ImageView) findViewById(R.id.serach_hide);
        clear = (ImageView) findViewById(R.id.clear);
        new initAccessoriesItemNameSpinner().execute();
        new initBrandNameSpinner().execute();
        new initItemNameSpinner().execute();
        new initFLocationSpinner().execute();
        new initFStatusSpinner().execute();
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
                final List<ViewAccessoriesListDAO> filteredList = new ArrayList<ViewAccessoriesListDAO>();
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

                mstudentList.setLayoutManager(new LinearLayoutManager(ViewAccessoriesActivity.this));
                viewAccessoriesListAdpter = new ViewAccessoriesListAdpter(ViewAccessoriesActivity.this, filteredList);
                mstudentList.setAdapter(viewAccessoriesListAdpter);
                viewAccessoriesListAdpter.notifyDataSetChanged();  // data set changed
            }
        });
    }
    //
    private class initAccessoriesItemNameSpinner extends AsyncTask<Void, Void, Void> {
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
            accessoriesItemNameResponse = serviceAccess.SendHttpPost(Config.URL_GETALLAITEMNAME, jsonLeadObj);
            Log.i("resp", "accessoriesItemNameResponse" + accessoriesItemNameResponse);

            if (accessoriesItemNameResponse.compareTo("") != 0) {
                if (isJSONValid(accessoriesItemNameResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                accessoriesItemnamelist = new ArrayList<>();
                                accessoriesItemnamelist.add(new AItemNameDAO("0", "Select Accessories Name"));
                                JSONArray LeadSourceJsonObj = new JSONArray(accessoriesItemNameResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    accessoriesItemnamelist.add(new AItemNameDAO(json_data.getString("id"), json_data.getString("accessory_name")));

                                }

                                jsonArray = new JSONArray(accessoriesItemNameResponse);

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
                            Toast.makeText(ViewAccessoriesActivity.this, "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ViewAccessoriesActivity.this, "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (accessoriesItemNameResponse.compareTo("") != 0) {



                // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<AItemNameDAO> adapter = new ArrayAdapter<AItemNameDAO>(ViewAccessoriesActivity.this, android.R.layout.simple_spinner_dropdown_item, accessoriesItemnamelist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                accessoriesDisplayItemName.setAdapter(adapter);

                accessoriesDisplayItemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        AItemNameDAO LeadSource = (AItemNameDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getItemName(), Toast.LENGTH_SHORT).show();
                        accessoriesItemNameId = LeadSource.getId();

                        if(!accessoriesItemNameId.equals("0"))
                        {
                            // Toast.makeText(getApplicationContext(),itemNameId+" "+locationNameId+" "+statusId, Toast.LENGTH_SHORT).show();
                            new getViewAccessoriesList().execute();
                        }
                        if(accessoriesItemNameId.equals("0"))
                        {
                            accessoriesItemNameId="0";
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

                    runOnUiThread(new Runnable() {

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

                                jsonArray = new JSONArray(aBrandNameResponse);

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
                            Toast.makeText(ViewAccessoriesActivity.this, "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ViewAccessoriesActivity.this, "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (aBrandNameResponse.compareTo("") != 0) {

                // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<ABrandNameDAO> adapter = new ArrayAdapter<ABrandNameDAO>(ViewAccessoriesActivity.this, android.R.layout.simple_spinner_dropdown_item, brandnamelist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                displayBrandName.setAdapter(adapter);

                displayBrandName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        ABrandNameDAO LeadSource = (ABrandNameDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getItemName(), Toast.LENGTH_SHORT).show();
                        itemBrandId = LeadSource.getId();
                        if(!itemBrandId.equals("0")) {
                            new getViewAccessoriesList().execute();
                        }

                        if(itemBrandId.equals("0"))
                        {
                            itemBrandId="0";
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
    //
    private class initItemNameSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            // mProgressDialog = new ProgressDialog(FixedAssetsActivity.this);
            // Set progressdialog title
            //  mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            //   mProgressDialog.setMessage("Loading...");
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
                ArrayAdapter<FItemNameDAO> adapter = new ArrayAdapter<FItemNameDAO>(ViewAccessoriesActivity.this, android.R.layout.simple_spinner_dropdown_item, itemnamelist);
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
                            //  Toast.makeText(getApplicationContext(),itemNameId+" "+locationNameId+" "+statusId, Toast.LENGTH_SHORT).show();
                            new getViewAccessoriesList().execute();
                        }
                        if(itemNameId.equals("0"))
                        {
                            itemNameId="0";
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                //mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                //  mProgressDialog.dismiss();
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
                ArrayAdapter<FALocationDAO> adapter = new ArrayAdapter<FALocationDAO>(ViewAccessoriesActivity.this, android.R.layout.simple_spinner_dropdown_item, flocationlist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                displayLocationName.setAdapter(adapter);
                displayLocationName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        FALocationDAO LeadSource = (FALocationDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getLocation_name(), Toast.LENGTH_SHORT).show();
                        locationNameId = LeadSource.getId();

                        if(!locationNameId.equals("0"))
                        {
                            // Toast.makeText(getApplicationContext(),itemNameId+" "+locationNameId+" "+statusId, Toast.LENGTH_SHORT).show();
                            new getViewAccessoriesList().execute();
                        }

                        if(locationNameId.equals("0"))
                        {
                            locationNameId="0";
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                // mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                //  mProgressDialog.dismiss();
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
                ArrayAdapter<FAStatusDAO> adapter = new ArrayAdapter<FAStatusDAO>(ViewAccessoriesActivity.this, android.R.layout.simple_spinner_dropdown_item, fstatuslist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                displayStatusName.setAdapter(adapter);
                displayStatusName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        FAStatusDAO LeadSource = (FAStatusDAO) parent.getSelectedItem();
                        //   Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getStatus(), Toast.LENGTH_SHORT).show();
                        statusId = LeadSource.getId();
                        if(!statusId.equals("0"))
                        {
                            // Toast.makeText(getApplicationContext(),itemNameId+" "+locationNameId+" "+statusId, Toast.LENGTH_SHORT).show();
                            new getViewAccessoriesList().execute();
                        }

                        if(statusId.equals("0"))
                        {
                            statusId="0";
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                //mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                //  mProgressDialog.dismiss();
            }
        }
    }
    //
//
    private class getViewAccessoriesList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ViewAccessoriesActivity.this);
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
                        put("accessory_id", accessoriesItemNameId);
                        put("brand_id", itemBrandId);
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
            viewAccessoriesAssestsResponse = serviceAccess.SendHttpPost(Config.URL_GETALLVIEWACCESSORIES, jsonLeadObj);
            Log.i("resp", "batchesListResponse" + viewAccessoriesAssestsResponse);
            if (viewAccessoriesAssestsResponse.compareTo("") != 0) {
                if (isJSONValid(viewAccessoriesAssestsResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                data.clear();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseViewAccessoriesList(viewAccessoriesAssestsResponse);
                                jsonArray = new JSONArray(viewAccessoriesAssestsResponse);

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
                viewAccessoriesListAdpter = new ViewAccessoriesListAdpter(ViewAccessoriesActivity.this, data);
                mstudentList.setAdapter(viewAccessoriesListAdpter);
                mstudentList.setLayoutManager(new LinearLayoutManager(ViewAccessoriesActivity.this));
                viewAccessoriesListAdpter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog

                totalcount.setText("");
                totalview.setVisibility(View.GONE);
                mProgressDialog.dismiss();
                data.clear(); // this list which you hava passed in Adapter for your listview
                viewAccessoriesListAdpter = new ViewAccessoriesListAdpter(ViewAccessoriesActivity.this, data);
                mstudentList.setAdapter(viewAccessoriesListAdpter);
                mstudentList.setLayoutManager(new LinearLayoutManager(ViewAccessoriesActivity.this));
                viewAccessoriesListAdpter.notifyDataSetChanged();

            }
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
}

