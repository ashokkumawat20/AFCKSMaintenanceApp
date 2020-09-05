package in.afckstechnologies.afcksmaintenanceapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.afckstechnologies.afcksmaintenanceapp.R;
import in.afckstechnologies.afcksmaintenanceapp.models.AccessoryDetailsDAO;
import in.afckstechnologies.afcksmaintenanceapp.view.AccessoryEntryView;


public class AccessoryListViewAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<AccessoryDetailsDAO> data;
    AccessoryDetailsDAO current;
    int currentPos = 0;
    String id, id1;
    String centerId;
    int ID;
    int number = 1;

    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String centerListResponse = "";
    boolean status;
    String message = "";
    String msg = "";

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;



    // create constructor to innitilize context and data sent from MainActivity
    public AccessoryListViewAdpter(Context context, List<AccessoryDetailsDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_accessory_details, parent, false);
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
        myHolder.brandName.setText(current.getBrand_name());
        myHolder.brandName.setTag(position);
        myHolder.accessoryName.setText(current.getAccessory_name());
        myHolder.accessoryName.setTag(position);
        myHolder.accessoryQty.setText(current.getQty());
        myHolder.accessoryQty.setTag(position);
        myHolder.editAccessory.setTag(position);

        myHolder.editAccessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("accessory_id",current.getAccessory_id());
                prefEditor.putString("brand_id",current.getBrand_id());
                prefEditor.putString("accessory_name",current.getAccessory_name());
                prefEditor.putString("brand_name",current.getBrand_name());
                prefEditor.putString("qty",current.getQty());
                prefEditor.commit();
              //  ((FragmentActivity)context).finish();
                AccessoryEntryView accessoryEntryView = new AccessoryEntryView();
                accessoryEntryView.show(((FragmentActivity) context).getSupportFragmentManager(), "accessoryEntryView");

               // Toast.makeText(context,current.getAccessory_name(),Toast.LENGTH_LONG).show();
            }
        });


    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }




    class MyHolder extends RecyclerView.ViewHolder {

        TextView brandName, accessoryName,accessoryQty;
        ImageView editAccessory;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            brandName = (TextView) itemView.findViewById(R.id.brandName);
            accessoryName = (TextView) itemView.findViewById(R.id.accessoryName);
            accessoryQty= (TextView) itemView.findViewById(R.id.accessoryQty);
            editAccessory=(ImageView)itemView.findViewById(R.id.editAccessory);


        }

    }

    // method to access in activity after updating selection
    public List<AccessoryDetailsDAO> getSservicelist() {
        return data;
    }

    //



}
