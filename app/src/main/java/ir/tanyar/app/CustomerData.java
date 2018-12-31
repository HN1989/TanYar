package ir.tanyar.app;

/**
 * Created by zaraksis on 06/13/2017.
 */

public class CustomerData {

    private String type,name,manager,state,city,area,adres,zipcode,phone,mobile,descrip,networker;

    public CustomerData(String type,String name,String manager,String state,String city,String area,String adres,String zipcode,String phone,String mobile,String descrip,String networker ) {

        this.type = type;
        this.name = name;
        this.manager = manager;
        this.state = state;
        this.city = city;
        this.area = area;
        this.adres = adres;
        this.zipcode = zipcode;
        this.phone = phone;
        this.mobile = mobile;
        this.descrip = descrip;
        this.networker = networker;

    }

    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public String getManager() {
        return manager;
    }
    public String getState() {
        return state;
    }
    public String getCity() {
        return city;
    }
    public String getArea() {
        return area;
    }
    public String getAdres() {
        return adres;
    }
    public String getZipcode() {
        return zipcode;
    }
    public String getPhone() {
        return phone;
    }
    public String getMobile() {
        return mobile;
    }
    public String getDescrip() {
        return descrip;
    }
    public String getNetworker() {
        return networker;
    }

}