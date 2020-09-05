package in.afckstechnologies.afcksmaintenanceapp.models;

/**
 * Created by admin on 3/7/2017.
 */

public class ABrandNameDAO {
    String id = "";
    String brand_name = "";
    public ABrandNameDAO() {

    }

    public ABrandNameDAO(String id, String brand_name) {
        this.id = id;
        this.brand_name = brand_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    @Override
    public String toString() {
        return brand_name;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ABrandNameDAO) {
            ABrandNameDAO c = (ABrandNameDAO) obj;
            if (c.getBrand_name().equals(brand_name) && c.getId() == id) return true;
        }

        return false;
    }
}
