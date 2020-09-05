package in.afckstechnologies.afcksmaintenanceapp.models;

/**
 * Created by Ashok on 4/18/2017.
 */

public class FixedAssestsDAO {

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
    String anti_id="";
    String serial_key="";
    String position="";
    String expiry_date="";
    public FixedAssestsDAO()
    {
    }

    public FixedAssestsDAO(String id, String identificationID, String locationID, String statusID, String userID, String dateTime, String qty, String stockQty, String itemNameID, String identification, String identification2, String identification3, String numbers, String itemName, String location, String status, String anti_id, String serial_key, String position, String expiry_date) {
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
        this.anti_id = anti_id;
        this.serial_key = serial_key;
        this.position = position;
        this.expiry_date = expiry_date;
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

    public String getAnti_id() {
        return anti_id;
    }

    public void setAnti_id(String anti_id) {
        this.anti_id = anti_id;
    }

    public String getSerial_key() {
        return serial_key;
    }

    public void setSerial_key(String serial_key) {
        this.serial_key = serial_key;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }
}
