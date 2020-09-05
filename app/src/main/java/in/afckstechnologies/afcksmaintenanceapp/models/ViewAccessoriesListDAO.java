package in.afckstechnologies.afcksmaintenanceapp.models;

/**
 * Created by Ashok on 4/18/2017.
 */

public class ViewAccessoriesListDAO {
    String fixed_assets_id="";
    String AccessoryQty="";
    String accessory_id="";
    String brand_id="";
    String accessory_name="";
    String brand_name="";
    String id="";
    String IdentificationID="";
    String LocationID="";
    String StatusID="";
    String UserID="";
    String DateTime="";
    String Qty="";
    String StockQty="";
    String ItemNameID="";
    String Identification="";
    String Identification2="";
    String Identification3="";
    String Numbers="";
    String ItemName="";
    String location="";
    String status="";
    public ViewAccessoriesListDAO()
    {
    }

    public ViewAccessoriesListDAO(String fixed_assets_id, String accessoryQty, String accessory_id, String brand_id, String accessory_name, String brand_name, String id, String identificationID, String locationID, String statusID, String userID, String dateTime, String qty, String stockQty, String itemNameID, String identification, String identification2, String identification3, String numbers, String itemName, String location, String status) {
        this.fixed_assets_id = fixed_assets_id;
        AccessoryQty = accessoryQty;
        this.accessory_id = accessory_id;
        this.brand_id = brand_id;
        this.accessory_name = accessory_name;
        this.brand_name = brand_name;
        this.id = id;
        IdentificationID = identificationID;
        LocationID = locationID;
        StatusID = statusID;
        UserID = userID;
        DateTime = dateTime;
        Qty = qty;
        StockQty = stockQty;
        ItemNameID = itemNameID;
        Identification = identification;
        Identification2 = identification2;
        Identification3 = identification3;
        Numbers = numbers;
        ItemName = itemName;
        this.location = location;
        this.status = status;
    }

    public String getFixed_assets_id() {
        return fixed_assets_id;
    }

    public void setFixed_assets_id(String fixed_assets_id) {
        this.fixed_assets_id = fixed_assets_id;
    }

    public String getAccessoryQty() {
        return AccessoryQty;
    }

    public void setAccessoryQty(String accessoryQty) {
        AccessoryQty = accessoryQty;
    }

    public String getAccessory_id() {
        return accessory_id;
    }

    public void setAccessory_id(String accessory_id) {
        this.accessory_id = accessory_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getAccessory_name() {
        return accessory_name;
    }

    public void setAccessory_name(String accessory_name) {
        this.accessory_name = accessory_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentificationID() {
        return IdentificationID;
    }

    public void setIdentificationID(String identificationID) {
        IdentificationID = identificationID;
    }

    public String getLocationID() {
        return LocationID;
    }

    public void setLocationID(String locationID) {
        LocationID = locationID;
    }

    public String getStatusID() {
        return StatusID;
    }

    public void setStatusID(String statusID) {
        StatusID = statusID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getStockQty() {
        return StockQty;
    }

    public void setStockQty(String stockQty) {
        StockQty = stockQty;
    }

    public String getItemNameID() {
        return ItemNameID;
    }

    public void setItemNameID(String itemNameID) {
        ItemNameID = itemNameID;
    }

    public String getIdentification() {
        return Identification;
    }

    public void setIdentification(String identification) {
        Identification = identification;
    }

    public String getIdentification2() {
        return Identification2;
    }

    public void setIdentification2(String identification2) {
        Identification2 = identification2;
    }

    public String getIdentification3() {
        return Identification3;
    }

    public void setIdentification3(String identification3) {
        Identification3 = identification3;
    }

    public String getNumbers() {
        return Numbers;
    }

    public void setNumbers(String numbers) {
        Numbers = numbers;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
