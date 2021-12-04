package model.to;

import java.io.Serializable;

public class BillTO implements Serializable {

    private long id;
    private long payId;
    private long price;
    private String type;
    private int payedFlag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPayId() {
        return payId;
    }

    public void setPayId(long payId) {
        this.payId = payId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPayedFlag() {
        return payedFlag;
    }

    public void setPayedFlag(int payedFlag) {
        this.payedFlag = payedFlag;
    }
}
