package model.to;

import java.io.Serializable;

public class CheckingAccountTO implements Serializable {

    private long id;
    private long money;
    private long openingDate;
    private long customerId;
    private int branchId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(long openingDate) {
        this.openingDate = openingDate;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

}
