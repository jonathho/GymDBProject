package model;

//CREATE TABLE Customer
//        (
//        cid       INTEGER,
//        phone_num INTEGER,
//        PRIMARY KEY (cid)
//        );
public class Customer {
    private final int cid;
    private final int phone_num;

    public Customer(int cid, int phone_num) {
        this.cid = cid;
        this.phone_num = phone_num;
    }

    public int getCid() {
        return cid;
    }

    public int getPhone_num() {
        return phone_num;
    }
}