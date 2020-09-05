package in.afckstechnologies.afcksmaintenanceapp.models;


public class AccessoryDetailsDAO {
    String accessory_name = "";
    String brand_name = "";
    String qty = "";
    String accessory_id="";
    String brand_id="";
    public AccessoryDetailsDAO() {

    }

    public AccessoryDetailsDAO(String accessory_name, String brand_name, String qty, String accessory_id, String brand_id) {
        this.accessory_name = accessory_name;
        this.brand_name = brand_name;
        this.qty = qty;
        this.accessory_id = accessory_id;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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
}
