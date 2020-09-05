package in.afckstechnologies.afcksmaintenanceapp.jsonparser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.afckstechnologies.afcksmaintenanceapp.models.AccessoryDetailsDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.FixedAssestsDAO;
import in.afckstechnologies.afcksmaintenanceapp.models.ViewAccessoriesListDAO;


public class JsonHelper {
    private ArrayList<ViewAccessoriesListDAO> viewAccessoriesListDAOs = new ArrayList<ViewAccessoriesListDAO>();
    private ViewAccessoriesListDAO viewAccessoriesListDAO;
    private ArrayList<AccessoryDetailsDAO> accessoryDetailsDAOArrayList = new ArrayList<AccessoryDetailsDAO>();
    private AccessoryDetailsDAO accessoryDetailsDAO;
    private ArrayList<FixedAssestsDAO> fixedAssestsDAOArrayList = new ArrayList<FixedAssestsDAO>();
    private FixedAssestsDAO fixedAssestsDAO;

    //ViewAccessoriesPaser
    public ArrayList<ViewAccessoriesListDAO> parseViewAccessoriesList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                viewAccessoriesListDAO = new ViewAccessoriesListDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                viewAccessoriesListDAO.setFixed_assets_id(json_data.getString("fixed_assets_id"));
                viewAccessoriesListDAO.setAccessoryQty(json_data.getString("AccessoryQty"));
                viewAccessoriesListDAO.setAccessory_id(json_data.getString("accessory_id"));
                viewAccessoriesListDAO.setBrand_id(json_data.getString("brand_id"));
                viewAccessoriesListDAO.setAccessory_name(json_data.getString("accessory_name"));
                viewAccessoriesListDAO.setBrand_name(json_data.getString("brand_name"));
                viewAccessoriesListDAO.setId(json_data.getString("id"));
                viewAccessoriesListDAO.setIdentification(json_data.getString("Identification"));
                viewAccessoriesListDAO.setIdentification2(json_data.getString("Identification2"));
                viewAccessoriesListDAO.setIdentification3(json_data.getString("Identification3"));
                viewAccessoriesListDAO.setDateTime(json_data.getString("DateTime"));
                viewAccessoriesListDAO.setItemNameID(json_data.getString("ItemNameID"));
                viewAccessoriesListDAO.setLocationID(json_data.getString("LocationID"));
                viewAccessoriesListDAO.setQty(json_data.getString("Qty"));
                viewAccessoriesListDAO.setStatusID(json_data.getString("StatusID"));
                viewAccessoriesListDAO.setUserID(json_data.getString("UserID"));
                viewAccessoriesListDAO.setIdentificationID(json_data.getString("IdentificationID"));
                viewAccessoriesListDAO.setStockQty(json_data.getString("StockQty"));
                viewAccessoriesListDAO.setNumbers("" + sequence + "(" + json_data.getString("id") + ")");
                viewAccessoriesListDAO.setItemName(json_data.getString("ItemName"));
                viewAccessoriesListDAO.setLocation(json_data.getString("location"));
                viewAccessoriesListDAO.setStatus(json_data.getString("status"));
                viewAccessoriesListDAOs.add(viewAccessoriesListDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return viewAccessoriesListDAOs;
    }

    //stcenterPaser
    public ArrayList<AccessoryDetailsDAO> parseAccessoryList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                accessoryDetailsDAO = new AccessoryDetailsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                accessoryDetailsDAO.setAccessory_name(json_data.getString("accessory_name"));
                accessoryDetailsDAO.setBrand_name(json_data.getString("brand_name"));
                accessoryDetailsDAO.setQty(json_data.getString("qty"));
                accessoryDetailsDAO.setAccessory_id(json_data.getString("accessory_id"));
                accessoryDetailsDAO.setBrand_id(json_data.getString("brand_id"));
                accessoryDetailsDAOArrayList.add(accessoryDetailsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return accessoryDetailsDAOArrayList;
    }

    //fixedAssestsPaser
    public ArrayList<FixedAssestsDAO> parseFixedAssetsList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                fixedAssestsDAO = new FixedAssestsDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                fixedAssestsDAO.setId(json_data.getString("id"));
                fixedAssestsDAO.setIdentification(json_data.getString("Identification"));
                fixedAssestsDAO.setIdentification2(json_data.getString("Identification2"));
                fixedAssestsDAO.setIdentification3(json_data.getString("Identification3"));
                fixedAssestsDAO.setDateTime(json_data.getString("DateTime"));
                fixedAssestsDAO.setItemNameID(json_data.getString("ItemNameID"));
                fixedAssestsDAO.setLocationID(json_data.getString("LocationID"));
                fixedAssestsDAO.setQty(json_data.getString("Qty"));
                fixedAssestsDAO.setStatusID(json_data.getString("StatusID"));
                fixedAssestsDAO.setUserID(json_data.getString("UserID"));
                fixedAssestsDAO.setIdentificationID(json_data.getString("IdentificationID"));
                fixedAssestsDAO.setStockQty(json_data.getString("StockQty"));
                fixedAssestsDAO.setNumbers("" + sequence + "(" + json_data.getString("id") + ")");
                fixedAssestsDAO.setItemName(json_data.getString("ItemName"));
                fixedAssestsDAO.setLocation(json_data.getString("location"));
                fixedAssestsDAO.setStatus(json_data.getString("status"));
                fixedAssestsDAO.setAnti_id(json_data.getString("anti_id"));
                fixedAssestsDAO.setExpiry_date(json_data.getString("expiry_date"));
                fixedAssestsDAO.setPosition(json_data.getString("position"));
                fixedAssestsDAO.setSerial_key(json_data.getString("serial_key"));
                fixedAssestsDAOArrayList.add(fixedAssestsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fixedAssestsDAOArrayList;
    }






}
