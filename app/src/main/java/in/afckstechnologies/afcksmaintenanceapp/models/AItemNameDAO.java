package in.afckstechnologies.afcksmaintenanceapp.models;

/**
 * Created by admin on 3/7/2017.
 */

public class AItemNameDAO {
    String id = "";
    String accessory_name = "";
    public AItemNameDAO() {

    }
    public AItemNameDAO(String id, String accessory_name) {
        this.id = id;
        this.accessory_name = accessory_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessory_name() {
        return accessory_name;
    }

    public void setAccessory_name(String accessory_name) {
        this.accessory_name = accessory_name;
    }

    @Override
    public String toString() {
        return accessory_name;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AItemNameDAO) {
            AItemNameDAO c = (AItemNameDAO) obj;
            if (c.getAccessory_name().equals(accessory_name) && c.getId() == id) return true;
        }

        return false;
    }
}
