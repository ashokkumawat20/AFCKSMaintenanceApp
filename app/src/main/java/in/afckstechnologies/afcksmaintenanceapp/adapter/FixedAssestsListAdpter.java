package in.afckstechnologies.afcksmaintenanceapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import in.afckstechnologies.afcksmaintenanceapp.R;
import in.afckstechnologies.afcksmaintenanceapp.models.FixedAssestsDAO;
import in.afckstechnologies.afcksmaintenanceapp.utils.Config;
import in.afckstechnologies.afcksmaintenanceapp.utils.WebClient;
import in.afckstechnologies.afcksmaintenanceapp.view.AccessoryDetailsView;
import in.afckstechnologies.afcksmaintenanceapp.view.AccessoryEntryView;
import in.afckstechnologies.afcksmaintenanceapp.view.AccessorySearchAntiKeysEntryView;
import in.afckstechnologies.afcksmaintenanceapp.view.AddSalesFixedAssetsView;
import in.afckstechnologies.afcksmaintenanceapp.view.IdentificationEntryViewUpdate;
import in.afckstechnologies.afcksmaintenanceapp.view.RepairOndamageFixedAssestsView;
import in.afckstechnologies.afcksmaintenanceapp.view.TransferFixedAssestsView;


/**
 * Created by admin on 3/18/2017.
 */

public class FixedAssestsListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<FixedAssestsDAO> data;
    FixedAssestsDAO current;
    int currentPos = 0;
    String id, id1;
    String centerId;
    int ID;
    int number = 1;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String sms_type = "";


    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String centerListResponse = "", callbackStatusResponse = "";
    boolean status;
    String message = "";
    String msg = "";
    String user_id = "";
    String batch_id = "";
    String s_id = "";


    // create constructor to innitilize context and data sent from MainActivity
    public FixedAssestsListAdpter(Context context, List<FixedAssestsDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_assests_details, parent, false);
        MyHolder holder = new MyHolder(view);


        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        if (!current.getIdentification().equals("")) {
            myHolder.i1.setVisibility(View.VISIBLE);
            myHolder.identification.setText(current.getIdentification());
            myHolder.identification.setTag(position);
        }
        if (!current.getIdentification2().equals("")) {
            myHolder.i2.setVisibility(View.VISIBLE);
            myHolder.identification2.setText(current.getIdentification2());
            myHolder.identification2.setTag(position);
        }
        if (!current.getIdentification3().equals("")) {
            myHolder.i3.setVisibility(View.VISIBLE);
            myHolder.identification3.setText(current.getIdentification3());
            myHolder.identification3.setTag(position);
        }

        myHolder.exitingQty.setText(current.getQty());
        myHolder.exitingQty.setTag(position);
        myHolder.sno.setText(current.getNumbers());
        myHolder.sno.setTag(position);
        myHolder.itemname.setText(current.getItemName());
        myHolder.itemname.setTag(position);
        myHolder.location.setText(current.getLocation());
        myHolder.location.setTag(position);
        myHolder.status.setText(current.getStatus());
        myHolder.status.setTag(position);

        myHolder.serialKeyL.setTag(position);
        myHolder.expiryDateL.setTag(position);
        myHolder.poitionL.setTag(position);


        myHolder.newSales.setTag(position);
        myHolder.oldOut.setTag(position);
        myHolder.onDamagerepairOut.setTag(position);
        myHolder.newaccessory.setTag(position);
        myHolder.viewaccessory.setTag(position);
        myHolder.updateAnti.setTag(position);

        String strOrig = current.getItemName();
        int laptop = strOrig.indexOf("Laptop");
        int pc = strOrig.indexOf("PC");
        if (laptop != -1 || pc != -1) {
            myHolder.updateAnti.setVisibility(View.VISIBLE);
        }

        if (!current.getAnti_id().equals("null")) {
            myHolder.updateAnti.setVisibility(View.GONE);
            myHolder.serialKeyL.setVisibility(View.VISIBLE);
            myHolder.expiryDateL.setVisibility(View.VISIBLE);
            myHolder.poitionL.setVisibility(View.VISIBLE);
            myHolder.serialKey.setText(current.getSerial_key());
            myHolder.serialKey.setTag(position);
            myHolder.expiryDate.setText(current.getExpiry_date());
            myHolder.expiryDate.setTag(position);
            myHolder.poition.setText(current.getPosition());
            myHolder.poition.setTag(position);
        }
        myHolder.newSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("identificationNameID", current.getIdentificationID());
                prefEditor.commit();
                if (!preferences.getString("locationNameID", "").equals("0") && !preferences.getString("statusNameID", "").equals("0")) {
                    AddSalesFixedAssetsView addSalesFixedAssetsView = new AddSalesFixedAssetsView();
                    addSalesFixedAssetsView.show(((FragmentActivity) context).getSupportFragmentManager(), "addSalesFixedAssetsView");
                } else {
                    Toast.makeText(context, "Please select current location and status", Toast.LENGTH_LONG).show();
                }
            }
        });
        myHolder.oldOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("identificationNameID", current.getIdentificationID());
                prefEditor.commit();
                if (!preferences.getString("locationNameID", "").equals("0") && !preferences.getString("statusNameID", "").equals("0")) {
                    TransferFixedAssestsView transferFixedAssestsView = new TransferFixedAssestsView();
                    transferFixedAssestsView.show(((FragmentActivity) context).getSupportFragmentManager(), "transferFixedAssestsView");
                } else {
                    Toast.makeText(context, "Please select current location and status", Toast.LENGTH_LONG).show();
                }

            }
        });
        myHolder.onDamagerepairOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("identificationNameID", current.getIdentificationID());
                prefEditor.commit();
                if (!preferences.getString("locationNameID", "").equals("0") && !preferences.getString("statusNameID", "").equals("0")) {
                    RepairOndamageFixedAssestsView transferFixedAssestsView = new RepairOndamageFixedAssestsView();
                    transferFixedAssestsView.show(((FragmentActivity) context).getSupportFragmentManager(), "transferFixedAssestsView");
                } else {
                    Toast.makeText(context, "Please select current location and status", Toast.LENGTH_LONG).show();
                }

            }
        });
        myHolder.newaccessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("fm_id", current.getId());
                prefEditor.commit();
                AccessoryEntryView accessoryEntryView = new AccessoryEntryView();
                accessoryEntryView.show(((FragmentActivity) context).getSupportFragmentManager(), "accessoryEntryView");

            }
        });

        myHolder.viewaccessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("fm_id", current.getId());
                prefEditor.commit();
                AccessoryDetailsView accessoryDetailsView = new AccessoryDetailsView();
                accessoryDetailsView.show(((FragmentActivity) context).getSupportFragmentManager(), "accessoryDetailsView");

            }
        });

        myHolder.updateAnti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("fm_id", current.getId());
                prefEditor.commit();
                AccessorySearchAntiKeysEntryView accessorySearchAntiKeysEntryView = new AccessorySearchAntiKeysEntryView();
                accessorySearchAntiKeysEntryView.show(((FragmentActivity) context).getSupportFragmentManager(), "accessorySearchAntiKeysEntryView");


            }
        });
        myHolder.identification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("IdentificationID", current.getIdentificationID());
                prefEditor.putString("Identification1", current.getIdentification());
                prefEditor.putString("Identification2", current.getIdentification2());
                prefEditor.putString("Identification3", current.getIdentification3());
                prefEditor.commit();
             //  Toast.makeText(context,""+current.getIdentificationID()+""+current.getIdentification(),Toast.LENGTH_LONG).show();
                IdentificationEntryViewUpdate identificationEntryViewUpdate = new IdentificationEntryViewUpdate();
                identificationEntryViewUpdate.show(((FragmentActivity) context).getSupportFragmentManager(), "identificationEntryViewUpdate");

            }
        });

    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView identification, identification2, identification3, exitingQty, sno, itemname, location, status, serialKey, expiryDate, poition;
        Button newSales, oldOut, onDamagerepairOut, newaccessory, viewaccessory, updateAnti;
        LinearLayout i1, i2, i3, serialKeyL, expiryDateL, poitionL;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            identification = (TextView) itemView.findViewById(R.id.identification);
            identification2 = (TextView) itemView.findViewById(R.id.identification2);
            identification3 = (TextView) itemView.findViewById(R.id.identification3);
            exitingQty = (TextView) itemView.findViewById(R.id.exitingQty);
            sno = (TextView) itemView.findViewById(R.id.sno);
            itemname = (TextView) itemView.findViewById(R.id.itemname);
            location = (TextView) itemView.findViewById(R.id.location);
            status = (TextView) itemView.findViewById(R.id.status);
            serialKey = (TextView) itemView.findViewById(R.id.serialKey);
            expiryDate = (TextView) itemView.findViewById(R.id.expiryDate);
            poition = (TextView) itemView.findViewById(R.id.poition);
            i1 = (LinearLayout) itemView.findViewById(R.id.i1);
            i2 = (LinearLayout) itemView.findViewById(R.id.i2);
            i3 = (LinearLayout) itemView.findViewById(R.id.i3);
            serialKeyL = (LinearLayout) itemView.findViewById(R.id.serialKeyL);
            expiryDateL = (LinearLayout) itemView.findViewById(R.id.expiryDateL);
            poitionL = (LinearLayout) itemView.findViewById(R.id.poitionL);
            newSales = (Button) itemView.findViewById(R.id.newSales);
            oldOut = (Button) itemView.findViewById(R.id.oldOut);
            newaccessory = (Button) itemView.findViewById(R.id.newaccessory);
            onDamagerepairOut = (Button) itemView.findViewById(R.id.onDamagerepairOut);
            viewaccessory = (Button) itemView.findViewById(R.id.viewaccessory);
            updateAnti = (Button) itemView.findViewById(R.id.updateAnti);

        }

    }

    //
    private class bookSeat extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
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
            final String transaction = "Book seat with Zero fees";
            // String date = dpicker.getText().toString();
            final String amount = "0";

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final String datezero = df.format(c.getTime());
            jsonLeadObj = new JSONObject() {
                {
                    try {

                        put("user_id", user_id);
                        put("batch_id", preferences.getString("curr_batch_id", ""));
                        put("trans_id", transaction);
                        put("pay_date", datezero);
                        put("amount", amount);
                        put("status", 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_BOOKING_BATCH, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerListResponse);


            if (centerListResponse.compareTo("") != 0) {
                if (isJSONValid(centerListResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(centerListResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(centerListResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                // Close the progressdialog
                mProgressDialog.dismiss();
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    //
    private class changeCallbackStatus extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
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
                        put("id", s_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            callbackStatusResponse = serviceAccess.SendHttpPost(Config.URL_UPDATECALLBACKSTUDENT, jsonLeadObj);
            Log.i("resp", "leadListResponse" + callbackStatusResponse);


            if (callbackStatusResponse.compareTo("") != 0) {
                if (isJSONValid(callbackStatusResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(callbackStatusResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(callbackStatusResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                removeAt(ID);
                // Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }


    public void removeAt(int position) {
        current = data.get(position);
        if (data.contains(current)) {
            data.remove(position);
            notifyItemRemoved(position);
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


}
