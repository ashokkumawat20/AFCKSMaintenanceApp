package in.afckstechnologies.afcksmaintenanceapp.models;

/**
 * Created by Ashok on 5/4/2017.
 */

public class AntiKeysDAO {
    String id = "";
    String serial_key = "";

    public AntiKeysDAO() {

    }

    public AntiKeysDAO(String id, String serial_key) {
        this.id = id;
        this.serial_key = serial_key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerial_key() {
        return serial_key;
    }

    public void setSerial_key(String serial_key) {
        this.serial_key = serial_key;
    }

    @Override
    public String toString() {
        return serial_key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AntiKeysDAO) {
            AntiKeysDAO c = (AntiKeysDAO) obj;
            if (c.getSerial_key().equals(serial_key))
                return true;
        }

        return false;
    }
}
