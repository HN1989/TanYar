package ir.tanyar.app;

/**
 * Created by zaraksis on 06/13/2017.
 */

public class CustomerModel {

    private int id;
    private String type,name,manager,state,city,area,adres,zipcode,phone,mobile,networker;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }
    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {this.state = state;}

    public String getCity() {
        return city;
    }
    public void setCity(String city) {this.city = city;}

    public String getArea() {return area;}
    public void setArea(String area) {this.area = area;}

    public String getAdres() {return adres;}
    public void setAdres(String adres) {this.adres = adres;}

    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {this.zipcode = zipcode;}

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {this.phone = phone;}

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {this.mobile = mobile;}

    public String getNetworker() {
        return networker;
    }
    public void setNetworker(String networker) {this.networker = networker;}



}