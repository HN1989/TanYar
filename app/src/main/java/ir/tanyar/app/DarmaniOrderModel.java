package ir.tanyar.app;

/**
 * Created by zaraksis on 06/13/2017.
 */

public class DarmaniOrderModel {

    private int id;
    private String row,code,name,count,size,price,total;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getRow() {
        return row;
    }
    public void setRow(String row) {
        this.row = row;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name;}

    public String getCount() {
        return count;
    }
    public void setCount(String count) {this.count = count;}

    public String getSize() {
        return size;
    }
    public void setSize(String size) {this.size = size;}

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {this.price = price;}

    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {this.total = total;}

}